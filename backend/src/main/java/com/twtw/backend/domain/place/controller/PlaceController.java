package com.twtw.backend.domain.place.controller;

import com.twtw.backend.domain.place.dto.client.SurroundPlaceRequest;
import com.twtw.backend.domain.place.dto.response.PlaceResponse;
import com.twtw.backend.domain.place.service.PlaceService;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("places")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("surround")
    @Cacheable(value = "surroundPlace", key = "{#surroundPlaceRequest}", cacheManager = "cacheManager", unless = "result.body.results.size() <= 0")
    public ResponseEntity<PlaceResponse> searchSurroundPlace(
            @ModelAttribute final SurroundPlaceRequest surroundPlaceRequest) {
        return ResponseEntity.ok(placeService.searchSurroundPlace(surroundPlaceRequest));
    }
}
