package org.comics.stock.controller;

import org.comics.stock.model.StockUpdate;
import org.comics.stock.model.dto.StockUpdateDTO;
import org.comics.stock.model.dto.StockUpdateModificationRequest;
import org.comics.stock.model.dto.StockUpdateCreateRequest;
import org.comics.stock.service.StockItemService;
import org.comics.stock.service.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/v1/update")
public class StockUpdateController {
    @Autowired
    StockUpdateService stockUpdateService;

    @Autowired
    StockItemService stockItemService;

    @Autowired
    MessageSource messages;

    // GET
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<StockUpdateDTO> getAllStockUpdates() {
        List<StockUpdateDTO> stockUpdateDTOList = stockUpdateService.listStockUpdateToListDTO(stockUpdateService.getAllStockUpdates());
        if(stockUpdateDTOList == null || stockUpdateDTOList.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.findAll.error.message", null, null), "Stock Update"));
        }
        return stockUpdateDTOList;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value="/{stockUpdateId}", method = RequestMethod.GET)
    public ResponseEntity<StockUpdateDTO> getStockUpdateById(@PathVariable("stockUpdateId") Long stockUpdateId) {
        Optional<StockUpdate> stockUpdateOpt = stockUpdateService.getStockUpdateById(stockUpdateId);

        StockUpdate stockUpdate = stockUpdateOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock update", stockUpdateId))
        );

        return ResponseEntity.ok(new StockUpdateDTO(stockUpdate));
    }

    // POST/add
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<String> addStockUpdate(@RequestBody StockUpdateCreateRequest stockUpdateCreateRequest) {
        if(stockItemService.itemExists(stockUpdateCreateRequest.getStockItemId())) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock item", stockUpdateCreateRequest.getStockItemId()));
        }

        if(!stockUpdateService.addStockUpdate(stockUpdateCreateRequest)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("stock-update.add.error.message", null, null)));
        }

        return ResponseEntity.ok("Update added");
    }

    // PUT/update
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<StockUpdateDTO> updateStockUpdate(@RequestBody StockUpdateModificationRequest newStockUpdateModReq) {
        Long stockUpdateId = newStockUpdateModReq.getStockUpdateId();
        Optional<StockUpdate> oldStockUpdateOpt = stockUpdateService.getStockUpdateById(stockUpdateId);
        StockUpdate oldStockUpdate = oldStockUpdateOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock update", stockUpdateId))
        );

        return ResponseEntity.ok(new StockUpdateDTO(stockUpdateService.updateStockUpdate(newStockUpdateModReq, oldStockUpdate)));
    }

    // DELETE
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value="/{stockUpdateId}")
    public ResponseEntity<String> deleteStockUpdate(@PathVariable("stockUpdateId") Long stockUpdateId) {
        if(!stockUpdateService.deleteStockUpdate(stockUpdateId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock update", stockUpdateId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Stock update", stockUpdateId));
    }
}
