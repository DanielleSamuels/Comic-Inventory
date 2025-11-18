package org.comics.library.service.client;

import org.comics.library.model.dto.StockItemDTO;
import org.comics.library.security.FeignSecurityConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "stock-service", configuration = FeignSecurityConfig.class)
public interface StockFeignClient {

    @GetMapping("/v1/item/comic/{comicId}")
    List<StockItemDTO> getStockItemsByComicId(@PathVariable("comicId") Long comicId);
}

