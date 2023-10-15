package com.api.handball.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table
public class PlayerMatchPerformance {
    @EmbeddedId
    private PlayerMatchKey id;
    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "person_id")
    private Player player;
    @ManyToOne
    @MapsId("matchId")
    @JoinColumn(name = "match_id")
    private Match match;

    private Integer scoreAccumulation;
    private Integer faultsAccumulation;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public PlayerMatchPerformance(){}

    public PlayerMatchKey getId() {
        return id;
    }

    public void setId(PlayerMatchKey id) {
        this.id = id;
    }

    public Integer getScoreAccumulation() {
        return scoreAccumulation;
    }

    public void setScoreAccumulation(Integer scoreAccumulation) {
        this.scoreAccumulation = scoreAccumulation;
    }

    public Integer getFaultsAccumulation() {
        return faultsAccumulation;
    }

    public void setFaultsAccumulation(Integer faultsAccumulation) {
        this.faultsAccumulation = faultsAccumulation;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
