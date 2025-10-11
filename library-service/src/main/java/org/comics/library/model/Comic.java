package org.comics.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity @Table(name = "comics")
public class Comic {
    // GENERAL
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comic_id")
    private Long comicId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "series_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Series series;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) // rarely used; only used for a unique title in addition to the series name or special editions
    @Column(name = "title")
    private String title;

    @Positive
    @Column(name = "issue")
    private Integer issue;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover_month", length = 3)
    private Month coverMonth;

    @Min(1900)
    @Column(name = "cover_year")
    private Integer coverYear;

    @DecimalMin("0.00")
    @Column(name = "cover_price", precision = 10, scale = 2)
    private BigDecimal coverPrice;

    @Column(name = "upc")
    private String upc;

    // VARIANT (all null/false if not a variant cover)
    @Column(name = "is_variant")
    private Boolean isVariant;

    @Column(name = "variant_name")
    private String variantName;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "variant_artist_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Creator variantArtist;

    @Column(name = "is_incentive")
    private Boolean isIncentive;

    @Column(name = "incentive_ratio")
    private String incentiveRatio;

    // LINK TO CREATORS
    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComicCreator> credits = new ArrayList<>();
}
