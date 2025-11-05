package org.comics.stock.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class StockItemAddRequest {
    private Long comicId;
    private Integer numInStock;
    private Integer numOrdered;
    private Integer numReserved;
    private BigDecimal listPrice;
    private Boolean forSale;
    private String itemNotes;
}
