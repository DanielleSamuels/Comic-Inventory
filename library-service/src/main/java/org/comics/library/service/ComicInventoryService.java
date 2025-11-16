package org.comics.library.service;

import org.comics.library.model.Comic;
import org.comics.library.model.dto.ComicDTO;
import org.comics.library.model.dto.ComicInventoryDTO;
import org.comics.library.model.dto.StockItemDTO;
import org.comics.library.repo.ComicRepository;
import org.comics.library.service.client.StockFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComicInventoryService {
    @Autowired
    ComicRepository comicRepo;

    @Autowired
    StockFeignClient stockFeignClient;

    @Autowired
    MessageSource messages;

    @CircuitBreaker(name = "stockService", fallbackMethod = "getComicInventoryFallback")
    @Retry(name = "retryStockService", fallbackMethod = "getComicInventoryFallback")
    @Bulkhead(name = "bulkheadStockService", type= Type.THREADPOOL, fallbackMethod = "getComicInventoryFallback")
    public ComicInventoryDTO getComicInventory(Long comicId) {
        Optional<Comic> comicOpt = comicRepo.findById(comicId);

        Comic comic = comicOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId))
        );

        List<StockItemDTO> items = stockFeignClient.getStockItemsByComicId(comicId);

        ComicInventoryDTO comicInventory = new ComicInventoryDTO(new ComicDTO(comic), items);

        return comicInventory;
    }

    public ComicInventoryDTO getComicInventoryFallback(Long comicId, Throwable t) {
        Optional<Comic> comicOpt = comicRepo.findById(comicId);
        Comic comic = comicOpt.orElseThrow(
                () -> new IllegalArgumentException(String.format(messages.getMessage("general.get.error.message", null, null), "comic", comicId))
        );

        List<StockItemDTO> fallbackItemList = new ArrayList<>();
        StockItemDTO fallbackItem = new StockItemDTO();
        fallbackItem.setComicId(comicId);
        fallbackItem.setNumInStock(0);
        fallbackItem.setNumOrdered(0);
        fallbackItem.setNumReserved(0);
        fallbackItem.setListPrice(new BigDecimal("0.00"));
        fallbackItem.setForSale(false);
        fallbackItem.setItemNotes("Sorry, stock service is currently unavailable");
        fallbackItem.setCreatedOn(Instant.now());
        fallbackItem.setLastUpdated(Instant.now());
        fallbackItemList.add(fallbackItem);

        ComicInventoryDTO fallbackComicInventory = new ComicInventoryDTO(new ComicDTO(comic), fallbackItemList);

        return fallbackComicInventory;
    }
}
