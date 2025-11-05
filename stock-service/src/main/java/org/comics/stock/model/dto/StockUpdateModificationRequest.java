package org.comics.stock.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.comics.stock.model.UpdateType;

// for StockUpdate updates
@Getter @Setter
public class StockUpdateModificationRequest {
    private UpdateType updateType;
    private String updateNotes;
}
