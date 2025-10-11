package org.comics.stock.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter @Setter
@Entity @Table(name = "stock_updates")
public class StockUpdate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_update_id")
    private Long stockUpdateId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stock_item_id", nullable = false)
    private StockItem stockItem;

    @NotNull @Enumerated(EnumType.STRING)
    @Column(name = "update_type")
    private UpdateType updateType; // reason for update

    @NotNull
    @Column(name = "quantity_delta", nullable = false)
    private Integer quantityDelta; // positive ints = increase in stock; negative ints = decrease in stock

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "prev_quantity", nullable = false)   // pulled from item
    private Integer prevQuantity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "new_quantity", nullable = false)   // calculated based on delta
    private Integer newQuantity;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant createdDate;

    @Column(name = "update_notes")
    private String updateNotes;
}
