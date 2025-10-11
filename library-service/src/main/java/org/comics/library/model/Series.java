package org.comics.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity @Table(name = "series")
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_id")
    private Long seriesId;

    @NotBlank
    @Column(name = "publisher", nullable = false)
    private String publisher;

    @NotBlank
    @Column(name = "series_name", nullable = false)
    private String seriesName;

    @Positive
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "volume")
    private Integer volume;
}
