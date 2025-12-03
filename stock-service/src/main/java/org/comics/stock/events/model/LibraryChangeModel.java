package org.comics.stock.events.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.comics.stock.model.utils.ChangeAction;

@Getter @Setter @ToString
public class LibraryChangeModel {
    private Long comicId;
    private String publisher;
    private String seriesName;
    private Integer volume;
    private Integer issue;
    private ChangeAction action;

    public LibraryChangeModel() {}

    public LibraryChangeModel(Long comicId, String publisher, String seriesName, Integer volume, Integer issue, ChangeAction action) {
        this.comicId = comicId;
        this.publisher = publisher;
        this.seriesName = seriesName;
        this.volume = volume;
        this.issue = issue;
        this.action = action;
    }
}