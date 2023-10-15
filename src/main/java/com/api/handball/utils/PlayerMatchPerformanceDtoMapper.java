package com.api.handball.utils;

import com.api.handball.entity.Match;
import com.api.handball.entity.Player;
import com.api.handball.entity.PlayerMatchPerformance;
import com.api.handball.entity.dto.PlayerMatchPerformanceDto;
import com.api.handball.service.MatchService;
import com.api.handball.service.PlayerService;
import com.api.handball.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// Todo : to review
@Component
public class PlayerMatchPerformanceDtoMapper {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private TeamService teamService;

    public PlayerMatchPerformanceDto map(PlayerMatchPerformance performance) {
        return new PlayerMatchPerformanceDto(
                performance.getId().getPlayerId(),
                performance.getId().getMatchId(),
                performance.getScoreAccumulation(),
                performance.getFaultsAccumulation()
        );
    }

    public PlayerMatchPerformance map(PlayerMatchPerformanceDto dto) {
        Player player = playerService.getPlayerById(dto.playerId());
        Match match = matchService.getMatchById(dto.matchId());


        PlayerMatchPerformance playerPerformance = new PlayerMatchPerformance();

        playerPerformance.setScoreAccumulation(dto.scoreAccumulation());
        playerPerformance.setFaultsAccumulation(dto.faultsAccumulation());
        playerPerformance.setMatch(match);
        playerPerformance.setPlayer(player);
        playerPerformance.setTeam(player.getTeam());

        return playerPerformance;
    }
}
