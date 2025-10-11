package org.comics.library.repo;

import org.comics.library.model.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CreatorRepository extends JpaRepository<Creator, Long> {

}
