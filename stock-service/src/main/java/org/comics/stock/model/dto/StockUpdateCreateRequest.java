package org.comics.stock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.comics.stock.model.UpdateType;

@Getter
@Setter
public class StockUpdateCreateRequest {
    private Long stockItemId;
    private UpdateType updateType;
    private Integer quantityDelta;
    private String updateNotes;
}
