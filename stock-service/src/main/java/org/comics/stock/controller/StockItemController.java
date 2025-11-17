package org.comics.stock.controller;

import org.comics.stock.model.StockItem;
import org.comics.stock.model.dto.StockItemCreateRequest;
import org.comics.stock.model.dto.StockItemModificationRequest;
import org.comics.stock.service.StockItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/item")
public class StockItemController {
    @Autowired
    StockItemService stockItemService;

    @Autowired
    MessageSource messages;

    // GET
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<StockItem> getAllStockItems() {
        return stockItemService.getAllStockItems();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/{stockItemId}", method = RequestMethod.GET)
    public ResponseEntity<Optional<StockItem>> getStockItemById(@PathVariable("stockItemId") Long stockItemId) {
        Optional<StockItem> stockItem = stockItemService.getStockItemById(stockItemId);
        if(stockItem == null) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock item", stockItemId));
        }
        return ResponseEntity.ok(stockItem);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/comic/{comicId}", method = RequestMethod.GET)
    public ResponseEntity<List<StockItem>> getStockItemsByComicId(@PathVariable Long comicId) {
        List<StockItem> stockItems = stockItemService.getStockItemsByComicId(comicId);
        if(stockItems == null || stockItems.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("stock-item.getComic.error.message", null, null)));
        }
        return ResponseEntity.ok(stockItems);
    }


    // POST/add
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StockItem> addStockItem(@RequestBody StockItemCreateRequest stockItemRequest) {
        return ResponseEntity.ok(stockItemService.addStockItemRequest(stockItemRequest));
    }

    // PUT/update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<StockItem> updateStockItem(@RequestBody StockItemModificationRequest stockItemRequest) {
        Long stockItemId = stockItemRequest.getStockItemId();

        Optional<StockItem> stockItemOpt = stockItemService.getStockItemById(stockItemId);
        StockItem stockItem = stockItemOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock item", stockItemId))
        );

        return ResponseEntity.ok(stockItemService.updateStockItem(stockItem, stockItemRequest));
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{stockItemId}")
    public ResponseEntity<String> deleteStockItem(@PathVariable("stockItemId") Long stockItemId) {
        if(!stockItemService.deleteStockItem(stockItemId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock item", stockItemId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Stock item", stockItemId));
    }
}
