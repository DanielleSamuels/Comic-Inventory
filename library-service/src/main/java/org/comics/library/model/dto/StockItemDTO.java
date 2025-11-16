package org.comics.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
public class StockItemDTO {
    private Long stockItemId;
    private Long comicId;
    private Integer numInStock;
    private Integer numOrdered;
    private Integer numReserved;
    private BigDecimal listPrice;
    private Boolean forSale;
    private String itemNotes;
    private Instant createdOn;
    private Instant lastUpdated;
}
