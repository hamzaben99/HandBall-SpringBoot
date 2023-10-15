package com.api.handball.utils;

import com.api.handball.entity.Club;
import com.api.handball.entity.dto.ClubDto;
import org.springframework.stereotype.Component;

@Component
public class ClubDtoMapper {
    public Club map(ClubDto dto) {
        Club club = new Club();
        club.setClubName(dto.getClubName());
        return club;
    }

    public ClubDto map(Club club) {
        return ClubDto.builder()
                .clubName(club.getClubName())
                .build();
    }
}
