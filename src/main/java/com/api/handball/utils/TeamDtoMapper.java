package com.api.handball.utils;

import com.api.handball.entity.Team;
import com.api.handball.entity.dto.TeamDto;
import com.api.handball.service.CategoryService;
import com.api.handball.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamDtoMapper {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ClubService clubService;

    public Team map(TeamDto dto) {
        Team team = new Team();

        team.setCategory(categoryService.getCategoryById(dto.getCategoryId()));
        team.setClub(clubService.getClubById(dto.getClubId()));

        return team;
    }

    public TeamDto map(Team team) {
        return TeamDto.builder()
                .categoryId(team.getCategory().getCategoryId())
                .clubId(team.getClub().getClubId())
                .build();
    }
}
