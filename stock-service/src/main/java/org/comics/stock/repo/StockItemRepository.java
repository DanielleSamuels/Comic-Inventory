package org.comics.stock.repo;

import org.comics.stock.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Long>  {

}
