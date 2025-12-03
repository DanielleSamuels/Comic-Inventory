package org.comics.library.events.source;

import org.comics.library.events.model.LibraryChangeModel;
import org.comics.library.model.Comic;
import org.comics.library.model.utils.ChangeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.cloud.stream.messaging.Source;

@Component
public class SimpleSourceBean {

    private Source source;

    private static final Logger log = LoggerFactory.getLogger(SimpleSourceBean.class);

    public SimpleSourceBean(Source source) { this.source = source; }

    public void publishLibraryChange(ChangeAction action, Comic comic) {
        log.info("Sending Kafka message {} for comicId: {}", action, comic.getComicId());

        LibraryChangeModel change = new LibraryChangeModel(
                comic.getComicId(),
                comic.getSeries().getPublisher(),
                comic.getSeries().getSeriesName(),
                comic.getSeries().getVolume(),
                comic.getIssue(),
                action
        );

        source.output()
                .send(MessageBuilder.withPayload(change).build());
    }
}
