package org.comics.stock.service;

import org.comics.stock.model.StockItem;
import org.comics.stock.model.dto.StockItemAddRequest;
import org.comics.stock.repo.StockItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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

    public StockItem addStockItemRequest(StockItemAddRequest stockItemRequest) {
        StockItem stockItem = new StockItem();
        stockItem.setComicId(stockItemRequest.getComicId());
        stockItem.setNumInStock(stockItemRequest.getNumInStock());
        stockItem.setNumReserved(stockItemRequest.getNumReserved());
        stockItem.setNumOrdered(stockItemRequest.getNumOrdered());
        stockItem.setListPrice(stockItemRequest.getListPrice());
        stockItem.setForSale(stockItemRequest.getForSale());
        stockItem.setItemNotes(stockItemRequest.getItemNotes());
        stockItem.setCreatedOn(Instant.now());
        stockItem.setLastUpdated(Instant.now());

        return stockItemRepo.save(stockItem);
    }

    // Get
    public List<StockItem> getAllStockItems() { return stockItemRepo.findAll(); }

    public Optional<StockItem> getStockItemById(Long id) { return stockItemRepo.findById(id); }

    public List<StockItem> getStockItemsByComicId(Long comicId) { return stockItemRepo.findByComicId(comicId); }

    // Update
    public StockItem updateStockItem(StockItem stockItem) { return stockItemRepo.save(stockItem); }

    // Delete
    public Boolean deleteStockItem(Long id) {
        if(!itemExists(id)) {
            return false;
        }
        stockItemRepo.deleteById(id);
        return true;
    }

    // Other
    public Boolean itemExists(Long itemId) {
        if(stockItemRepo.existsById(itemId)) {
            return true;
        }
        return false;
    }
}
