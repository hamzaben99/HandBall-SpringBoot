package com.api.handball.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerMatchKey implements Serializable {
    @Column(name = "player_id")
    private Long playerId;
    @Column(name = "match_id")
    private Long matchId;

    public PlayerMatchKey(){}

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerMatchKey that = (PlayerMatchKey) o;
        return playerId.equals(that.playerId) && matchId.equals(that.matchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, matchId);
    }
}
