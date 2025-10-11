package org.comics.stock.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.comics.stock.model.StockUpdate;
import org.comics.stock.model.UpdateType;

import java.time.Instant;

@Getter
@Setter
public class StockUpdateDTO {
    private Long stockUpdateId;
    private Long stockItemId;
    private UpdateType updateType;
    private Instant createdDate;
    private Integer quantityDelta;
    private Integer prevQuantity;
    private Integer newQuantity;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String updateNotes;

    public StockUpdateDTO(StockUpdate u) {
        stockUpdateId = u.getStockUpdateId();
        stockItemId = u.getStockItem().getStockItemId();
        updateType = u.getUpdateType();
        createdDate = u.getCreatedDate();
        quantityDelta = u.getQuantityDelta();
        prevQuantity = u.getPrevQuantity();
        newQuantity = u.getNewQuantity();
        updateNotes = u.getUpdateNotes();
    }
}
