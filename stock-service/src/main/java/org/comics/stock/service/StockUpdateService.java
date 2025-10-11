package org.comics.stock.service;

import org.comics.stock.model.StockItem;
import org.comics.stock.model.StockUpdate;
import org.comics.stock.model.dto.StockUpdateDTO;
import org.comics.stock.repo.StockItemRepository;
import org.comics.stock.repo.StockUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StockUpdateService {
    @Autowired
    StockUpdateRepository stockUpdateRepo;

    @Autowired
    StockItemRepository stockItemRepo;

    // Add
    public Boolean addStockUpdate(StockUpdate stockUpdate) {
        StockItem item = stockUpdate.getStockItem();
        Integer prevQty = item.getNumInStock();
        Integer newQty = prevQty + stockUpdate.getQuantityDelta();
        if(newQty < 0) {
            return false;
        }

        stockUpdate.setNewQuantity(newQty);
        stockUpdate.setPrevQuantity(prevQty);
        stockUpdate.setCreatedDate(Instant.now());
        stockUpdateRepo.save(stockUpdate);

        return true;
    }

    // Get
    public List<StockUpdate> getAllStockUpdates() { return stockUpdateRepo.findAll(); }

    public Optional<StockUpdate> getStockUpdateById(Long id) { return stockUpdateRepo.findById(id); }

    // Update
    public Boolean updateStockUpdate(StockUpdate newStockUpdate, StockUpdate oldStockUpdate) {
        if(newStockUpdate.getStockItem() != oldStockUpdate.getStockItem() ||
                newStockUpdate.getQuantityDelta() != oldStockUpdate.getQuantityDelta() ||
                newStockUpdate.getPrevQuantity() != oldStockUpdate.getPrevQuantity() ||
                newStockUpdate.getNewQuantity() != oldStockUpdate.getNewQuantity()
        ) {
            return false;
        }

        stockUpdateRepo.save(newStockUpdate);
        return true;
    }

    // Delete
    public Boolean deleteStockUpdate(Long id) {
        if(!stockUpdateRepo.existsById(id)) {
            return false;
        }
        stockUpdateRepo.deleteById(id);
        return true;
    }

    // other
    public List<StockUpdateDTO> listStockUpdateToListDTO(List<StockUpdate> stockUpdates) {
        if (stockUpdates == null || stockUpdates.isEmpty()) {
            return Collections.emptyList();
        }

        List<StockUpdateDTO> stockUpdateDTOList = new ArrayList<>();

        for(StockUpdate stockUpdate : stockUpdates) {
            StockUpdateDTO stockUpdateDTO = new StockUpdateDTO(stockUpdate);
            stockUpdateDTOList.add(stockUpdateDTO);
        }

        return stockUpdateDTOList;
    }
}
