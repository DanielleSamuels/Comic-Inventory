package org.comics.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.comics.library.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// for returning all comic info without recursion in the JSON
@Getter @Setter
public class ComicDTO {
    private Long comicId;
    //private SeriesDTO series;
    private Series series;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String title;
    private Integer issue;
    private LocalDate releaseDate;
    private String coverDate;
    private BigDecimal coverPrice;
    private String upc;
    private Boolean isVariant;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String variantName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String variantArtist;
    private Boolean isIncentive;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String incentiveRatio;
    private List<CreditDTO> credits = new ArrayList<>();

    public ComicDTO(Comic c) {
        // set as is
        comicId = c.getComicId();
        title = c.getTitle();
        issue = c.getIssue();
        releaseDate = c.getReleaseDate();
        coverPrice = c.getCoverPrice();
        upc = c.getUpc();
        isVariant = c.getIsVariant();
        variantName = c.getVariantName();
        isIncentive = c.getIsIncentive();
        incentiveRatio = c.getIncentiveRatio();

        // combine month and year if possible
        if((c.getCoverMonth() != null) && (c.getCoverYear() != null)) {
            coverDate = c.getCoverMonth().toString() + " " + c.getCoverYear().toString();
        } else if(c.getCoverMonth() != null) {
            coverDate = c.getCoverMonth().toString();
        } else if(c.getCoverYear() != null) {
            coverDate = c.getCoverYear().toString();
        }

        // only include variant artist name
        if(c.getVariantArtist() != null) {
            variantArtist = c.getVariantArtist().getName();
        }

        // series
        if(c.getSeries() != null) {
            //SeriesDTO sDTO = new SeriesDTO(c.getSeries());
            //series = sDTO;
            series = c.getSeries();
        }

        // creators
        if(c.getCredits() != null){
            List<ComicCreator> creds = c.getCredits();
            for(ComicCreator cred : creds) {
                CreditDTO ccDTO = new CreditDTO(cred);
                credits.add(ccDTO);
            }
        }
    }
}
