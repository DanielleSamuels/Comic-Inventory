package org.comics.stock.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

// for updating a StockItem; doesn't include numInStock because numInStock must be updated via a StockUpdate creation
@Getter @Setter
public class StockItemModificationRequest {
    private Long comicId;
    private Integer numOrdered;
    private Integer numReserved;
    private BigDecimal listPrice;
    private Boolean forSale;
    private String itemNotes;
}
