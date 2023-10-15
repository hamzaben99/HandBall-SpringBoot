package com.api.handball.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class Player extends Person {
    private String licenceNumber;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private final Set<PlayerMatchPerformance> playerMatchPerformances = new HashSet<>();

    public Player() {}

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<PlayerMatchPerformance> getPlayerMatchPerformances() {
        return playerMatchPerformances;
    }

    public void addPlayerMatchPerformance(PlayerMatchPerformance playerMatchPerformance) {
        playerMatchPerformances.add(playerMatchPerformance);
    }
}
