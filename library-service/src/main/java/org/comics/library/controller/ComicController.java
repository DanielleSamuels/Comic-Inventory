package org.comics.library.controller;

import org.comics.library.model.Comic;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.utils.Response;
import org.comics.library.service.ComicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/comics")
public class ComicController {
    @Autowired
    ComicService comicService;

    @Autowired
    MessageSource messages;

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
