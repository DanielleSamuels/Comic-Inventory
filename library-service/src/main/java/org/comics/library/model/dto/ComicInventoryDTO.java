package org.comics.library.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// for returning comic and stock info together
@Getter @Setter
public class ComicInventoryDTO {
    private ComicDTO comic;
    private List<StockItemDTO> stockItems;

    public ComicInventoryDTO(ComicDTO comic, List<StockItemDTO> stockItems) {
        this.comic = comic;
        this.stockItems = new ArrayList<>(stockItems);
    }
}
