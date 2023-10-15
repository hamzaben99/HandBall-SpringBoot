package com.api.handball.entity.dto;

public record PlayerMatchPerformanceDto(Long playerId, Long matchId, Integer scoreAccumulation, Integer faultsAccumulation) {}
