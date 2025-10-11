package org.comics.library.repo;

import org.comics.library.model.Comic;
import org.comics.library.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    //List<Series> findBySeries_NameContainingIgnoreCaseAndVolume(String series_name, Integer volume);

}
