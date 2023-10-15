package com.api.handball.entity.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class MatchDto {
    private LocalDate matchDate;
    private Long seasonId;
    private Integer journee;

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getJournee() {
        return journee;
    }

    public void setJournee(Integer journee) {
        this.journee = journee;
    }
}
