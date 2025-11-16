package org.comics.library.controller;

import org.comics.library.model.Comic;
import org.comics.library.model.Series;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.dto.SeriesDTO;
import org.comics.library.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1/series")
public class SeriesController {
    @Autowired
    SeriesService seriesService;

    @Autowired
    MessageSource messages;

    // GET
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<SeriesDTO> getAllSeries() {
        List<SeriesDTO> seriesDTOList = seriesService.listSeriesToListDTO(seriesService.getAllSeries());
        if(seriesDTOList == null || seriesDTOList.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.findAll.error.message", null, null), "Series"));
        }

        return seriesDTOList;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/{seriesId}", method = RequestMethod.GET)
    public ResponseEntity<SeriesDTO> getSeriesById(@PathVariable("seriesId") Long seriesId) {
        Optional<Series> seriesOpt = seriesService.getSeriesById(seriesId);

        Series series = seriesOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "series", seriesId))
        );

        SeriesDTO seriesDTO = seriesService.getSeriesDTO(series);
        return ResponseEntity.ok(seriesDTO);
    }

    // POST/add
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SeriesDTO> addSeries(@RequestBody Series series) {
        if(seriesService.seriesExists(series)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("series.add.error.message", null, null)));
        }
        seriesService.addSeries(series);
        SeriesDTO seriesDTO = seriesService.getSeriesDTO(series);
        return ResponseEntity.ok(seriesDTO);
    }

    // PUT/update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<SeriesDTO> updateSeries(@RequestBody Series series) {
        SeriesDTO seriesDTO = seriesService.getSeriesDTO(series);
        seriesService.addSeries(series);
        return ResponseEntity.ok(seriesDTO);
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{seriesId}")
    public ResponseEntity<String> deleteSeries(@PathVariable("seriesId") Long seriesId) {
        if(seriesService.hasComics(seriesId)) {

            throw new IllegalArgumentException(String.format(messages.getMessage("series.delete.error.message", null, null), seriesId));
        }

        if(!seriesService.deleteSeries(seriesId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "series", seriesId));
        }

        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Series", seriesId));
    }
}
