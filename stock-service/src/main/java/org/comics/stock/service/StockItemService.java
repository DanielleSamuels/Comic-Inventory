package org.comics.stock.service;

import org.comics.stock.model.StockItem;
import org.comics.stock.repo.StockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StockItemService {
    @Autowired
    StockItemRepository stockItemRepo;

    // Add
    public StockItem addStockItem(StockItem stockItem) {
        return stockItemRepo.save(stockItem);
    }

    // Get
    public List<StockItem> getAllStockItems() { return stockItemRepo.findAll(); }

    public Optional<StockItem> getStockItemById(Long id) { return stockItemRepo.findById(id); }

    // Update
    public StockItem updateStockItem(StockItem stockItem) { return stockItemRepo.save(stockItem); }

    // Delete
    public Boolean deleteStockItem(Long id) {
        if(!stockItemRepo.existsById(id)) {
            return false;
        }
        stockItemRepo.deleteById(id);
        return true;
    }
}
