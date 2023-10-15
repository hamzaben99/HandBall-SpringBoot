package com.api.handball.utils;

import com.api.handball.entity.Match;
import com.api.handball.entity.dto.MatchDto;
import com.api.handball.entity.dto.PlayerMatchPerformanceDto;
import com.api.handball.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MatchDtoMapper {
    @Autowired
    private SeasonService seasonService;

    public Match map(MatchDto dto) {
        Match match = new Match();

        match.setMatchDate(dto.getMatchDate());
        match.setJournee(dto.getJournee());
        match.setSeason(seasonService.getSeasonById(dto.getSeasonId()));

        return match;
    }

    public MatchDto map(Match match) {
        return MatchDto.builder()
                .seasonId(match.getSeason().getSeasonId())
                .journee(match.getJournee())
                .matchDate(match.getMatchDate())
                .build();
    }
}
