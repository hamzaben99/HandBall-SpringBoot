package com.api.handball.service;

import com.api.handball.entity.Person;
import com.api.handball.entity.Player;
import com.api.handball.entity.Team;
import com.api.handball.entity.dto.PersonDto;
import com.api.handball.entity.dto.PlayerDto;
import com.api.handball.repository.PlayerRepository;
import com.api.handball.utils.PlayerDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PersonService personService;
    private final TeamService teamService;
    private final PlayerDtoMapper playerMapper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, PersonService personService, TeamService teamService, PlayerDtoMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.personService = personService;
        this.teamService = teamService;
        this.playerMapper = playerMapper;
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElse(null);
    }

    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    // Adds a player that doesn't exist in the people table (basically adds a person and assign it as a player)
    @Transactional
    public Long addPlayerPerson(PersonDto personDto, String licenceNumber, Long teamId) {
        Long personId = personService.addPerson(personDto);
        return addPlayer(PlayerDto.builder()
                .PersonId(personId)
                .teamId(teamId)
                .licenceNumber(licenceNumber)
                .build());
    }

    // Adds a player that exists in the people table.
    @Transactional
    public Long addPlayer(PlayerDto playerDto) {
        return playerRepository.save(playerMapper.map(playerDto)).getPersonId();
    }

    @Transactional
    public void updatePlayer(PlayerDto updatedPlayerDto, Long playerId) {
        Player updatedPlayer = playerRepository.findById(playerId).orElseThrow();

        Team team = teamService.getTeamById(updatedPlayerDto.getTeamId());
        if (team == null)
            throw new RuntimeException("team with id " + updatedPlayerDto.getTeamId() + " doesn't exist.");

        updatedPlayer.setLicenceNumber(updatedPlayerDto.getLicenceNumber());
        updatedPlayer.setTeam(team);
        updatedPlayer.setClub(team.getClub());

        playerRepository.save(updatedPlayer);
    }

    @Transactional
    public void deletePlayerById(Long playerId) {
        personService.deletePersonById(playerId);
    }
}
