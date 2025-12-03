package org.comics.stock.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {

    @Input("libraryChangeInput")
    SubscribableChannel comicChangeInput();

}
