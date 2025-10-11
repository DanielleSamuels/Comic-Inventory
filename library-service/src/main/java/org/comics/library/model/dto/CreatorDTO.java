package org.comics.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.comics.library.model.Creator;

// for returning all creator info without recursion in the JSON
@Getter
@Setter
public class CreatorDTO {
    private Long creatorId;
    private String name;
    private String primaryRole;

    public CreatorDTO(Creator cr) {
        creatorId = cr.getCreatorId();
        name = cr.getName();
        primaryRole = cr.getPrimaryRole();
    }
}
