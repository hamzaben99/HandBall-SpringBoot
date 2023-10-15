package com.api.handball.utils;

import com.api.handball.entity.Player;
import com.api.handball.entity.dto.PlayerDto;
import com.api.handball.entity.dto.PlayerMatchPerformanceDto;
import com.api.handball.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PlayerDtoMapper {
    @Autowired
    private TeamService teamService;

    public Player map(PlayerDto dto) {
        Player player = new Player();

        player.setLicenceNumber(dto.getLicenceNumber());
        player.setTeam(teamService.getTeamById(dto.getTeamId()));

        return player;
    }

    public PlayerDto map(Player player) {
        return PlayerDto.builder()
                .licenceNumber(player.getLicenceNumber())
                .teamId(player.getTeam().getTeamId())
                .PersonId(player.getPersonId())
                .build();
    }
}
