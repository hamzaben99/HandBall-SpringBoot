package com.api.handball.repository;

import com.api.handball.entity.PlayerMatchKey;
import com.api.handball.entity.PlayerMatchPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerMatchPerformanceRepository extends JpaRepository<PlayerMatchPerformance, PlayerMatchKey> {
    List<PlayerMatchPerformance> findById_MatchId(Long matchId);
    List<PlayerMatchPerformance> findById_PlayerId(Long playerId);
    void deleteById_PlayerId(Long playerId);
    void deleteById_MatchId(Long MatchId);
}
