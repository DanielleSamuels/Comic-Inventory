package org.comics.library.controller;

import org.comics.library.model.Comic;
import org.comics.library.model.ComicCreator;
import org.comics.library.model.Creator;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.utils.Response;
import org.comics.library.repo.ComicCreatorRepository;
import org.comics.library.service.ComicService;
import org.comics.library.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/comics")
public class ComicController {
    @Autowired
    ComicService comicService;

    @Autowired
    CreatorService creatorService;

    @Autowired
    MessageSource messages;

    @Autowired
    ComicCreatorRepository comicCreatorRepo;

    // GET
    @RequestMapping(method = RequestMethod.GET)
    public List<ComicDTO> getAllComics() {
        List<ComicDTO> comicDTOList = comicService.listComicToListDTO(comicService.getAllComics());
        if(comicDTOList == null || comicDTOList.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.findAll.error.message", null, null), "Comic"));
        }
        return comicDTOList;
    }

    @RequestMapping(value="/{comicId}", method = RequestMethod.GET)
    public ResponseEntity<ComicDTO> getComicById(@PathVariable("comicId") Long comicId) {
        Optional<Comic> comicOpt = comicService.getComicById(comicId);

        Comic comic = comicOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId))
        );

        ComicDTO comicDTO = new ComicDTO(comic);
        return ResponseEntity.ok(comicDTO);
    }

    // POST/add
    @PostMapping
    public ResponseEntity<ComicDTO> addComic(@RequestBody Comic comic) {
        return ResponseEntity.ok(new ComicDTO(comicService.addComic(comic)));
    }

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
    @PutMapping
    public ResponseEntity<ComicDTO> updateComic(@RequestBody Comic comic) {
        return ResponseEntity.ok(new ComicDTO(comicService.updateComic(comic)));
    }

    // DELETE
    @DeleteMapping(value="/{comicId}")
    public ResponseEntity<String> deleteComic(@PathVariable("comicId") Long comicId) {
        if(!comicService.deleteComic(comicId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Comic", comicId));
    }
}
