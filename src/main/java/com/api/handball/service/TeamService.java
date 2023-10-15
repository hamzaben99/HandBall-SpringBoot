package com.api.handball.service;

import com.api.handball.entity.Category;
import com.api.handball.entity.Club;
import com.api.handball.entity.Team;
import com.api.handball.entity.dto.TeamDto;
import com.api.handball.repository.TeamRepository;
import com.api.handball.utils.TeamDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final TeamDtoMapper mapper;
    private final ClubService clubService;
    private final CategoryService categoryService;

    @Autowired
    public TeamService(TeamRepository teamRepository, TeamDtoMapper mapper, ClubService clubService, CategoryService categoryService) {
        this.teamRepository = teamRepository;
        this.mapper = mapper;
        this.clubService = clubService;
        this.categoryService = categoryService;
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }

    @Transactional
    public Long addTeam(TeamDto teamDto) {
        return teamRepository.save(mapper.map(teamDto)).getTeamId();
    }

    @Transactional
    public void UpdateTeam(TeamDto updatedTeamDto, Long teamId) {
        Team updatedTeam = teamRepository.findById(teamId).orElseThrow();

        Club club = clubService.getClubById(updatedTeamDto.getClubId());
        Category category = categoryService.getCategoryById(updatedTeamDto.getCategoryId());

        if (club == null || category == null)
            throw new RuntimeException("invalid input.");

        updatedTeam.setClub(club);
        updatedTeam.setCategory(category);

        teamRepository.save(updatedTeam);
    }

    @Transactional
    public void deleteTeamById(Long teamId) {
        if (teamRepository.existsById(teamId))
            throw new RuntimeException("id " + teamId + " doesn't exist");

        teamRepository.deleteById(teamId);
    }

    void savePreUpdatedTeam(Team team) {
        teamRepository.save(team);
    }
}
