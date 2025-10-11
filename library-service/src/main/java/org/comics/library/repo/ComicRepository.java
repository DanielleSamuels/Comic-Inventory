package org.comics.library.repo;

import org.comics.library.model.Comic;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    @EntityGraph(attributePaths = {"series"})
    List<Comic> findByTitleIgnoreCaseContaining(String title);

    @EntityGraph(attributePaths = {"series"})
    Long countBySeries_SeriesIdAndIsVariantFalse(Long seriesId);

    @EntityGraph(attributePaths = {
            "series",
            "variantArtist",
            "credits",
            "credits.creator"
    })
    List<Comic> findAll();

    @EntityGraph(attributePaths = {
            "series",
            "variantArtist",
            "credits",
            "credits.creator"
    })
    Optional<Comic> findById(Long id);
}