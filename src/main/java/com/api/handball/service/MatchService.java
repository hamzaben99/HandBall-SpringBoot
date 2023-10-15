package com.api.handball.service;

import com.api.handball.entity.Match;
import com.api.handball.entity.Season;
import com.api.handball.entity.dto.MatchDto;
import com.api.handball.repository.MatchRepository;
import com.api.handball.utils.MatchDtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final MatchDtoMapper matchMapper;
    private final SeasonService seasonService;

    @Autowired
    public MatchService(MatchRepository matchRepository, MatchDtoMapper matchMapper, SeasonService seasonService) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
        this.seasonService = seasonService;
    }

    public Match getMatchById(Long matchId) {
        return matchRepository.findById(matchId).orElse(null);
    }

    public List<Match> getMatches() {
        return matchRepository.findAll();
    }

    @Transactional
    public Long addMatch(MatchDto matchDto) {
        return matchRepository.save(matchMapper.map(matchDto)).getMatchId();
    }

    @Transactional
    public void updateMatch(MatchDto matchDto, Long matchId) {
        Match updatedMatch = matchRepository.findById(matchId).orElseThrow();

        Season season = seasonService.getSeasonById(matchDto.getSeasonId());
        if (season == null)
            throw new RuntimeException("season with id " + matchDto.getSeasonId() + " doesn't exist.");

        updatedMatch.setMatchDate(matchDto.getMatchDate());
        updatedMatch.setJournee(matchDto.getJournee());
        updatedMatch.setSeason(season);

        matchRepository.save(updatedMatch);
    }

    @Transactional
    public void deleteMatchById(Long matchId) {
        matchRepository.deleteById(matchId);
    }
}
