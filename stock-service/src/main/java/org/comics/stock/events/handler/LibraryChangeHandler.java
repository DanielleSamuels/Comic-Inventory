package org.comics.stock.events.handler;

import org.comics.stock.events.CustomChannels;
import org.comics.stock.events.model.LibraryChangeModel;
import org.comics.stock.model.utils.ChangeAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannels.class)
public class LibraryChangeHandler {

    private static final Logger log = LoggerFactory.getLogger(LibraryChangeHandler.class);

    @StreamListener("libraryChangeInput")
    public void handleLibraryChange(LibraryChangeModel event) {
        ChangeAction action = event.getAction();
        Long comicId = event.getComicId();

        switch (action) {
            case CREATED:
                log.info("New comic with comicId {} available in library", comicId);
                break;
            case UPDATED:
                log.info("Comic with comicId {} was updated in library", comicId);
                break;
            case DELETED:
                log.info("Comic with comicId {} was deleted from library", comicId);
            case GET:
                break;
            default:
                log.warn("Unknown changeAction {}", action);
        }
    }
}
