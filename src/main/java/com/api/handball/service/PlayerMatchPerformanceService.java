package com.api.handball.service;

import com.api.handball.entity.Match;
import com.api.handball.entity.Player;
import com.api.handball.entity.PlayerMatchKey;
import com.api.handball.entity.PlayerMatchPerformance;
import com.api.handball.entity.dto.PlayerMatchPerformanceDto;
import com.api.handball.repository.PlayerMatchPerformanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerMatchPerformanceService {
    private final PlayerMatchPerformanceRepository playerMatchPerformanceRepository;
    private final PlayerService playerService;
    private final MatchService matchService;

    @Autowired
    public PlayerMatchPerformanceService(PlayerMatchPerformanceRepository playerMatchPerformanceRepository,
                         PlayerService playerService,
                         MatchService matchService) {
        this.playerMatchPerformanceRepository = playerMatchPerformanceRepository;
        this.playerService = playerService;
        this.matchService = matchService;
    }

    public PlayerMatchPerformance getPerformanceById(PlayerMatchKey id) {
        return playerMatchPerformanceRepository.findById(id).orElse(null);
    }

    public List<PlayerMatchPerformance> getPerformanceByPlayerId(Long playerId) {
        return playerMatchPerformanceRepository.findById_PlayerId(playerId);
    }

    public List<PlayerMatchPerformance> getPerformanceByMatchId(Long matchId) {
        return playerMatchPerformanceRepository.findById_MatchId(matchId);
    }

    public List<PlayerMatchPerformance> getPerformances() {
        return playerMatchPerformanceRepository.findAll();
    }

    @Transactional
    public void addPlayerPerformance(PlayerMatchPerformanceDto performanceDto) {
        Player player = playerService.getPlayerById(performanceDto.playerId());
        Match match = matchService.getMatchById(performanceDto.matchId());
        if (player == null || match == null)
            throw new RuntimeException("invalid input.");

        PlayerMatchPerformance performance = new PlayerMatchPerformance();
        performance.setPlayer(player);
        performance.setMatch(match);
        performance.setTeam(player.getTeam());
        performance.setScoreAccumulation(performanceDto.scoreAccumulation());
        performance.setFaultsAccumulation(performanceDto.faultsAccumulation());

        playerMatchPerformanceRepository.save(performance);
    }

    @Transactional
    public void deletePerformanceById(PlayerMatchKey id) {
        playerMatchPerformanceRepository.deleteById(id);
    }

    @Transactional
    public void deletePerformanceByPlayerId(Long PlayerId) {
        playerMatchPerformanceRepository.deleteById_PlayerId(PlayerId);
    }

    @Transactional
    public void deletePerformanceByMatchId(Long MatchId) {
        playerMatchPerformanceRepository.deleteById_MatchId(MatchId);
    }

    @Transactional
    public void updatePerformance(PlayerMatchPerformanceDto updatedPerformance, PlayerMatchKey id) {
        PlayerMatchPerformance performance = playerMatchPerformanceRepository.findById(id).orElseThrow();

        performance.setScoreAccumulation(updatedPerformance.scoreAccumulation());
        performance.setFaultsAccumulation(updatedPerformance.faultsAccumulation());

        playerMatchPerformanceRepository.save(performance);
    }
}
