package org.comics.library.service;

import org.comics.library.model.Creator;
import org.comics.library.repo.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CreatorService {
    @Autowired
    CreatorRepository creatorRepo;

    // Add
    public Creator addCreator(Creator creator) {
        return creatorRepo.save(creator);
    }

    // Get
    public List<Creator> getAllCreators() { return creatorRepo.findAll(); }

    public Optional<Creator> getCreatorById(Long id) { return creatorRepo.findById(id); }

    // Update
    public Creator updateCreator(Creator creator) { return creatorRepo.save(creator); }

    // Delete
    @Transactional
    public Boolean deleteCreator(Long id) { // soft delete
        if(!creatorRepo.existsById(id)) {
            return false;
        }
        creatorRepo.deleteById(id);
        return true;
    }
}
