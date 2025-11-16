package org.comics.library.controller;

import org.comics.library.model.Creator;
import org.comics.library.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/v1/creator")
public class CreatorController {
    @Autowired
    CreatorService creatorService;

    @Autowired
    MessageSource messages;

    // GET
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<Creator> getAllCreators() {
        return creatorService.getAllCreators();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/{creatorId}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Creator>> getCreatorById(@PathVariable("creatorId") Long creatorId) {
        Optional<Creator> creator = creatorService.getCreatorById(creatorId);
        if(creator.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "creator", creatorId));
        }
        return ResponseEntity.ok(creator);
    }

    // POST/add
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Creator> addCreator(@RequestBody Creator creator) {
        return ResponseEntity.ok(creatorService.addCreator(creator));
    }

    // PUT/update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<Creator> updateCreator(@RequestBody Creator creator) {
        return ResponseEntity.ok(creatorService.updateCreator(creator));
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{creatorId}")
    public ResponseEntity<String> deleteCreator(@PathVariable("creatorId") Long creatorId) {
        if(!creatorService.deleteCreator(creatorId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "creator", creatorId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Creator", creatorId));
    }
}
