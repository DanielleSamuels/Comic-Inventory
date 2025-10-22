package org.comics.library.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.comics.library.model.Month;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

// handles comic info passed through JSON to avoid series and variantArtist setting issues
@Getter
@Setter
public class ComicRequest {
    @NotNull
    private Long seriesId;
    private String title;
    @Positive
    private Integer issue;
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private Month coverMonth;
    @Min(1900)
    private Integer coverYear;
    @DecimalMin("0.00")
    private BigDecimal coverPrice;
    private String upc;
    private Boolean isVariant;
    private String variantName;
    private Long variantArtistId;
    private Boolean isIncentive;
    private String incentiveRatio;
}
