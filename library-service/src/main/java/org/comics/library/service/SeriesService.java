package org.comics.library.service;

import org.comics.library.model.Comic;
import org.comics.library.model.Series;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.dto.SeriesDTO;
import org.comics.library.repo.ComicRepository;
import org.comics.library.repo.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SeriesService {
    @Autowired
    SeriesRepository seriesRepo;

    @Autowired
    ComicRepository comicRepo;

    // Add
    public Series addSeries(Series series) {
        return seriesRepo.save(series);
    }

    // Get
    public List<Series> getAllSeries() { return seriesRepo.findAll(); }

    public Optional<Series> getSeriesById(Long id) { return seriesRepo.findById(id); }

    // Update
    public Series updateSeries(Series series) { return seriesRepo.save(series); }

    // Delete
    public Boolean deleteSeries(Long id) {
        if(!seriesRepo.existsById(id)) {
            return false;
        }
        seriesRepo.deleteById(id);
        return true;
    }

    public SeriesDTO getSeriesDTO(Series s) {
        if(seriesRepo.existsById(s.getSeriesId())) {
            Long numIssues = comicRepo.countBySeries_SeriesIdAndIsVariantFalse(s.getSeriesId());
            SeriesDTO sDTO = new SeriesDTO(s, numIssues);
        }
        return null;
    }

    public List<SeriesDTO> listSeriesToListDTO(List<Series> series) {
        if (series == null || series.isEmpty()) {
            return Collections.emptyList();
        }

        List<SeriesDTO> seriesDTOList = new ArrayList<>();

        for(Series aSeries : series) {
            SeriesDTO seriesDTO = getSeriesDTO(aSeries);
            seriesDTOList.add(seriesDTO);
        }
        return seriesDTOList;
    }
}
