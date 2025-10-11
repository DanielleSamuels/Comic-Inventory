package org.comics.stock.repo;

import org.comics.stock.model.StockUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockUpdateRepository extends JpaRepository<StockUpdate, Long> {
}
