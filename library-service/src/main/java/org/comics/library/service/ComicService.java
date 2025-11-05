package org.comics.library.service;

import org.comics.library.model.Comic;
import org.comics.library.model.Creator;
import org.comics.library.model.Series;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.dto.ComicRequest;
import org.comics.library.model.dto.SeriesDTO;
import org.comics.library.repo.ComicRepository;
import org.comics.library.repo.CreatorRepository;
import org.comics.library.repo.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ComicService {
    @Autowired
    ComicRepository comicRepo;

    // Add
    public Comic addComic(Comic comic) {
        return comicRepo.save(comic);
    }

    public Comic addComicRequest(ComicRequest comicRequest, Series series, Creator variantArtist) {
        Comic comic = new Comic();
        comic.setSeries(series);
        comic.setTitle(comicRequest.getTitle());
        comic.setIssue(comicRequest.getIssue());
        comic.setReleaseDate(comicRequest.getReleaseDate());
        comic.setCoverMonth(comicRequest.getCoverMonth());
        comic.setCoverYear(comicRequest.getCoverYear());
        comic.setCoverPrice(comicRequest.getCoverPrice());
        comic.setUpc(comicRequest.getUpc());
        comic.setIsVariant(comicRequest.getIsVariant());
        comic.setVariantName(comicRequest.getVariantName());
        comic.setVariantArtist(variantArtist);
        comic.setIsIncentive(comicRequest.getIsIncentive());
        comic.setIncentiveRatio(comicRequest.getIncentiveRatio());

        return addComic(comic);
    }

    // Get
    public List<Comic> getAllComics() { return comicRepo.findAll(); }

    public Optional<Comic> getComicById(Long id) { return comicRepo.findById(id); }

    // Update
    public Comic updateComicRequest(ComicRequest comicRequest, Series series, Creator variantArtist, Comic comic) {
        comic.setSeries(series);
        comic.setTitle(comicRequest.getTitle());
        comic.setIssue(comicRequest.getIssue());
        comic.setReleaseDate(comicRequest.getReleaseDate());
        comic.setCoverMonth(comicRequest.getCoverMonth());
        comic.setCoverYear(comicRequest.getCoverYear());
        comic.setCoverPrice(comicRequest.getCoverPrice());
        comic.setUpc(comicRequest.getUpc());
        comic.setIsVariant(comicRequest.getIsVariant());
        comic.setVariantName(comicRequest.getVariantName());
        comic.setVariantArtist(variantArtist);
        comic.setIsIncentive(comicRequest.getIsIncentive());
        comic.setIncentiveRatio(comicRequest.getIncentiveRatio());

        return comicRepo.save(comic);
    }

    // Delete
    public Boolean deleteComic(Long id) {
        if (!comicRepo.existsById(id)) {
            return false;
        }
        comicRepo.deleteById(id);
        return true;
    }

    // Other
    public List<ComicDTO> listComicToListDTO(List<Comic> comics) {
        if (comics == null || comics.isEmpty()) {
            return Collections.emptyList();
        }

        List<ComicDTO> comicDTOList = new ArrayList<>();

        for(Comic comic : comics) {
            ComicDTO comicDTO = new ComicDTO(comic);
            comicDTOList.add(comicDTO);
        }

        return comicDTOList;
    }

    public Long getNumberOfIssuesForSeriesById(Long seriesId) {
        return comicRepo.countBySeries_SeriesIdAndIsVariantFalse(seriesId);
    }

    public Optional<Comic> getComicObj(Long id) {
        return comicRepo.findById(id);
    }
}
