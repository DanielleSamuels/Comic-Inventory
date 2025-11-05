package org.comics.stock.controller;

import org.comics.stock.model.StockItem;
import org.comics.stock.model.dto.StockItemAddRequest;
import org.comics.stock.service.StockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
public class StockItemController {
    @Autowired
    StockItemService stockItemService;

    @Autowired
    MessageSource messages;

    // GET
    @RequestMapping(method = RequestMethod.GET)
    public List<StockItem> getAllStockItems() {
        return stockItemService.getAllStockItems();
    }

    @RequestMapping(value="/{stockItemId}", method = RequestMethod.GET)
    public ResponseEntity<Optional<StockItem>> getStockItemById(@PathVariable("stockItemId") Long stockItemId) {
        Optional<StockItem> stockItem = stockItemService.getStockItemById(stockItemId);
        if(stockItem == null) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock item", stockItemId));
        }
        return ResponseEntity.ok(stockItem);
    }

    // POST/add
    @PostMapping
    public ResponseEntity<StockItem> addStockItem(@RequestBody StockItemAddRequest stockItemRequest) {
        return ResponseEntity.ok(stockItemService.addStockItemRequest(stockItemRequest));
    }

    // PUT/update
    @PutMapping
    public ResponseEntity<StockItem> updateStockItem(@RequestBody StockItem stockItem) {
        return ResponseEntity.ok(stockItemService.updateStockItem(stockItem));
    }

    // DELETE
    @DeleteMapping(value="/{stockItemId}")
    public ResponseEntity<String> deleteStockItem(@PathVariable("stockItemId") Long stockItemId) {
        if(!stockItemService.deleteStockItem(stockItemId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock item", stockItemId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Stock item", stockItemId));
    }
}
