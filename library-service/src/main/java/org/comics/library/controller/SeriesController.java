package org.comics.library.controller;

import org.comics.library.model.Comic;
import org.comics.library.model.Series;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.dto.SeriesDTO;
import org.comics.library.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/series")
public class SeriesController {
    @Autowired
    SeriesService seriesService;

    @Autowired
    MessageSource messages;

    // GET
    @RequestMapping(method = RequestMethod.GET)
    public List<SeriesDTO> getAllSeries() {
        List<SeriesDTO> seriesDTOList = seriesService.listSeriesToListDTO(seriesService.getAllSeries());
        if(seriesDTOList == null || seriesDTOList.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.findAll.error.message", null, null), "Series"));
        }
        return seriesDTOList;
    }

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
    @PostMapping
    public ResponseEntity<SeriesDTO> addSeries(@RequestBody Series series) {
        SeriesDTO seriesDTO = seriesService.getSeriesDTO(series);
        seriesService.addSeries(series);
        return ResponseEntity.ok(seriesDTO);
    }

    // PUT/update
    @PutMapping
    public ResponseEntity<SeriesDTO> updateSeries(@RequestBody Series series) {
        SeriesDTO seriesDTO = seriesService.getSeriesDTO(series);
        seriesService.addSeries(series);
        return ResponseEntity.ok(seriesDTO);
    }

    // DELETE
    @DeleteMapping(value="/{seriesId}")
    public ResponseEntity<String> deleteSeries(@PathVariable("seriesId") Long seriesId) {
        if(!seriesService.deleteSeries(seriesId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "series", seriesId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Series", seriesId));
    }
}
