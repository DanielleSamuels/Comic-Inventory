package org.comics.library.repo;

import org.comics.library.model.ComicCreator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicCreatorRepository extends JpaRepository<ComicCreator, Long> { }
