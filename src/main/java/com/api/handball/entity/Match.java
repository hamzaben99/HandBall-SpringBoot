package com.api.handball.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchId;
    @Column(nullable = false)
    private LocalDate matchDate;

    @ManyToOne
    @JoinColumn(name = "season_id")
    private Season season;
    private Integer journee;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL)
    private final Set<PlayerMatchPerformance> playerMatchPerformances = new HashSet<>();

    public Match() {}

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Integer getJournee() {
        return journee;
    }

    public void setJournee(Integer journee) {
        this.journee = journee;
    }

    public Set<PlayerMatchPerformance> getPlayerMatchPerformances() {
        return playerMatchPerformances;
    }

    public void addPlayerMatchPerformance(PlayerMatchPerformance playerMatchPerformance) {
        playerMatchPerformances.add(playerMatchPerformance);
    }
}
