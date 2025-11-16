package org.comics.library.controller;

import org.comics.library.model.Comic;
import org.comics.library.model.ComicCreator;
import org.comics.library.model.Creator;
import org.comics.library.model.Series;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.dto.ComicInventoryDTO;
import org.comics.library.model.dto.ComicRequest;
import org.comics.library.model.utils.Response;
import org.comics.library.repo.ComicCreatorRepository;
import org.comics.library.service.ComicInventoryService;
import org.comics.library.service.ComicService;
import org.comics.library.service.CreatorService;
import org.comics.library.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/v1/comics")
public class ComicController {
    @Autowired
    ComicService comicService;

    @Autowired
    CreatorService creatorService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    ComicInventoryService comicInventoryService;

    @Autowired
    MessageSource messages;

    @Autowired
    ComicCreatorRepository comicCreatorRepo;

    // GET
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<ComicDTO> getAllComics() {
        List<ComicDTO> comicDTOList = comicService.listComicToListDTO(comicService.getAllComics());
        if(comicDTOList == null || comicDTOList.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.findAll.error.message", null, null), "Comic"));
        }
        return comicDTOList;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/{comicId}", method = RequestMethod.GET)
    public ResponseEntity<ComicDTO> getComicById(@PathVariable("comicId") Long comicId) {
        Optional<Comic> comicOpt = comicService.getComicById(comicId);

        Comic comic = comicOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId))
        );

        ComicDTO comicDTO = new ComicDTO(comic);
        return ResponseEntity.ok(comicDTO);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/{comicId}/items", method = RequestMethod.GET)
    public ResponseEntity<ComicInventoryDTO> getComicInventory(@PathVariable Long comicId) {
        return ResponseEntity.ok(comicInventoryService.getComicInventory(comicId));
    }

    // POST/add
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ComicDTO> addComic(@RequestBody ComicRequest comicRequest) {
        // add series
        Optional<Series> seriesOpt = seriesService.getSeriesById(comicRequest.getSeriesId());
        Series series = seriesOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("comic.add.series.error.message", null, null)))
        );

        // add variant artist
        Creator variantArtist;
        if(comicRequest.getVariantArtistId() != null) {
            Optional<Creator> creatorOpt = creatorService.getCreatorById(comicRequest.getVariantArtistId());
            variantArtist = creatorOpt.orElseThrow(
                    () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "creator", comicRequest.getVariantArtistId()))
            );
        } else {
            variantArtist = null;
        }

        return ResponseEntity.ok(new ComicDTO(comicService.addComicRequest(comicRequest, series, variantArtist)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{comicId}/credit/{creatorId}/role/{role}")
    ResponseEntity<String> addCredit(@PathVariable("comicId") Long comicId, @PathVariable("creatorId") Long creatorId, @PathVariable("role") String role) {
        Optional<Comic> comicOpt = comicService.getComicObj(comicId);
        Optional<Creator> creatorOpt = creatorService.getCreatorById(creatorId);

        Comic comic = comicOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId))
        );

        Creator creator = creatorOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "creator", creatorId))
        );


        ComicCreator cc = new ComicCreator();
        cc.setComic(comic);
        cc.setCreator(creator);
        cc.setRole(role);

        comicCreatorRepo.save(cc);
        return ResponseEntity.ok("Creator Added to Comic");
    }


    // PUT/update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value="/{comicId}")
    public ResponseEntity<ComicDTO> updateComic(@PathVariable("comicId") Long comicId, @RequestBody ComicRequest comicRequest) {

        // find comic
        Optional<Comic> comicOpt = comicService.getComicObj(comicId);
        Comic comic = comicOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId))
        );

        // find series
        Optional<Series> seriesOpt = seriesService.getSeriesById(comicRequest.getSeriesId());
        Series series = seriesOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("comic.add.series.error.message", null, null)))
        );

        // find variantArtist if applicable
        Creator variantArtist;
        if(comicRequest.getVariantArtistId() != null) {
            Optional<Creator> creatorOpt = creatorService.getCreatorById(comicRequest.getVariantArtistId());
            variantArtist = creatorOpt.orElseThrow(
                    () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "creator", comicRequest.getVariantArtistId()))
            );
        } else {
            variantArtist = null;
        }

        return ResponseEntity.ok(new ComicDTO(comicService.updateComicRequest(comicRequest, series, variantArtist, comic)));
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{comicId}")
    public ResponseEntity<String> deleteComic(@PathVariable("comicId") Long comicId) {
        if(!comicService.deleteComic(comicId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Comic", comicId));
    }
}
