package com.api.handball.entity.dto;

import lombok.Builder;

@Builder
public class ClubDto {
    private String clubName;

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }
}
