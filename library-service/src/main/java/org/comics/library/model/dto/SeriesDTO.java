package org.comics.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.comics.library.model.Series;

// for returning series info without returning volume if it does not apply to series
@Getter
@Setter
public class SeriesDTO {
    private Long seriesId;
    private String publisher;
    private String seriesName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer volume;
    private Long numberOfIssues;

    public SeriesDTO(Series s, Long noi) {
        seriesId = s.getSeriesId();
        publisher = s.getPublisher();
        seriesName = s.getSeriesName();
        volume = s.getVolume();
        numberOfIssues = noi;
    }
}
