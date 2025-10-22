package org.comics.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
@Entity @Table(name = "stock_items")
public class StockItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_item_id")
    private Long stockItemId;

    @NotNull
    @Column(name = "comic_id", nullable = false)
    private Long comicId;

    @Min(0)
    @Column(name = "num_in_stock", nullable = false)  // number of item onsite
    private Integer numInStock;

    @Min(0)
    @Column(name = "num_ordered", nullable = false) // number of item on the way
    private Integer numOrdered;

    @Min(0)
    @Column(name = "num_reserved", nullable = false) // number of item reserved by customers; cannot sell/reserve item if numInStock + numOrdered <= numReserved
    private Integer numReserved;

    @DecimalMin("0.00")
    @Column(name = "list_price", precision = 10, scale = 2)  // usually same as cover price, unless back-issue, incentive cover, or rare/exclusive; if null assume cover price; if no coverPrice or listPrice, reject any sales
    private BigDecimal list_price;

    @NotBlank
    @Column(name = "for_sale", nullable = false)  // usually true unless item is not available for pre-order or if item has been pulled from shelves
    Boolean forSale;

    @Column(name = "item_notes")
    private String itemNotes;

    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdOn;

    @LastModifiedDate
    @Column(name = "last_updated", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant lastUpdated;
}
