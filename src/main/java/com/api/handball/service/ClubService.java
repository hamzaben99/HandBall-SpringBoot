package com.api.handball.service;

import com.api.handball.entity.Club;
import com.api.handball.entity.dto.ClubDto;
import com.api.handball.repository.ClubRepository;
import com.api.handball.utils.ClubDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ClubDtoMapper mapper;

    @Autowired
    public ClubService(ClubRepository clubRepository, ClubDtoMapper mapper) {
        this.clubRepository = clubRepository;
        this.mapper = mapper;
    }

    public List<Club> getClubs() {
        return clubRepository.findAll();
    }

    public Club getClubById(Long clubId) {
        return clubRepository.findById(clubId).orElse(null);
    }

    @Transactional
    public Long addClub(ClubDto clubDto) {
        return clubRepository.save(mapper.map(clubDto)).getClubId();
    }

    @Transactional
    public void deleteClubById(Long clubId) {
        if (clubRepository.existsById(clubId))
            throw new RuntimeException("id " + clubId + " doesn't exist");

        clubRepository.deleteById(clubId);
    }

    @Transactional
    public void updateClub(ClubDto updatedClubDto, Long clubId) {
        Club updatedClub = clubRepository.findById(clubId).orElseThrow();
        updatedClub.setClubName(updatedClubDto.getClubName());
        clubRepository.save(updatedClub);
    }
}
