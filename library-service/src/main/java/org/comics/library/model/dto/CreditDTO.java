package org.comics.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.comics.library.model.ComicCreator;
import org.comics.library.model.Creator;

// for credits (type ComicCreator) in ComicDTO
@Getter
@Setter
public class CreditDTO {
    private Long comicCreatorId;
    private Long creatorId;
    private String creatorName;
    private String role;
    public CreditDTO(ComicCreator cc) {
        comicCreatorId = cc.getComicCreatorId();
        role = cc.getRole();

        Creator cr = cc.getCreator();
        creatorId = cr.getCreatorId();
        creatorName = cr.getName();
    }
}
