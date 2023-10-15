package com.api.handball.service;

import com.api.handball.entity.Season;
import com.api.handball.repository.SeasonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonService {
    private final SeasonRepository seasonRepository;

    @Autowired
    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public Season getSeasonById(Long seasonId) {
        return seasonRepository.findById(seasonId).orElse(null);
    }

    public List<Season> getSeasons() {
        return seasonRepository.findAll();
    }

    @Transactional
    public Long addSeason() {
        return seasonRepository.save(new Season()).getSeasonId();
    }

    @Transactional
    public void deleteSeasonById(Long seasonId) {
        seasonRepository.deleteById(seasonId);
    }
}
