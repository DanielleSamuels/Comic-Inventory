package org.comics.stock.controller;

import org.comics.stock.model.StockUpdate;
import org.comics.stock.model.dto.StockUpdateDTO;
import org.comics.stock.service.StockUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/update")
public class StockUpdateController {
    @Autowired
    StockUpdateService stockUpdateService;

    @Autowired
    MessageSource messages;

    // GET
    @RequestMapping(method = RequestMethod.GET)
    public List<StockUpdateDTO> getAllStockUpdates() {
        List<StockUpdateDTO> stockUpdateDTOList = stockUpdateService.listStockUpdateToListDTO(stockUpdateService.getAllStockUpdates());
        if(stockUpdateDTOList == null || stockUpdateDTOList.isEmpty()) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.findAll.error.message", null, null), "Stock Update"));
        }
        return stockUpdateDTOList;
    }

    @RequestMapping(value="/{stockUpdateId}", method = RequestMethod.GET)
    public ResponseEntity<StockUpdateDTO> getStockUpdateById(@PathVariable("stockUpdateId") Long stockUpdateId) {
        Optional<StockUpdate> stockUpdateOpt = stockUpdateService.getStockUpdateById(stockUpdateId);

        StockUpdate stockUpdate = stockUpdateOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock update", stockUpdateId))
        );

        return ResponseEntity.ok(new StockUpdateDTO(stockUpdate));
    }

    // POST/add
    @PostMapping
    public ResponseEntity<StockUpdateDTO> addStockUpdate(@RequestBody StockUpdate stockUpdate) {
        if(!stockUpdateService.addStockUpdate(stockUpdate)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("stock-update.add.error.message", null, null)));
        }

        return ResponseEntity.ok(new StockUpdateDTO(stockUpdate));
    }

    // PUT/update
    @PutMapping
    public ResponseEntity<StockUpdateDTO> updateStockUpdate(@RequestBody StockUpdate newStockUpdate) {
        Long id = newStockUpdate.getStockUpdateId();
        Optional<StockUpdate> oldStockUpdateOpt = stockUpdateService.getStockUpdateById(id);
        StockUpdate oldStockUpdate = oldStockUpdateOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock update", id))
        );

        if(!stockUpdateService.updateStockUpdate(newStockUpdate, oldStockUpdate)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("stock-update.update.error.message", null, null)));
        }

        return ResponseEntity.ok(new StockUpdateDTO(newStockUpdate));
    }

    // DELETE
    @DeleteMapping(value="/{stockUpdateId}")
    public ResponseEntity<String> deleteStockUpdate(@PathVariable("stockUpdateId") Long stockUpdateId) {
        if(!stockUpdateService.deleteStockUpdate(stockUpdateId)) {
            throw new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "stock update", stockUpdateId));
        }
        return ResponseEntity.ok(String.format(messages.getMessage("general.delete.message", null, null), "Stock update", stockUpdateId));
    }
}
