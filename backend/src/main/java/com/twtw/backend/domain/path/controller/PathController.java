package com.twtw.backend.domain.path.controller;

import com.twtw.backend.domain.path.dto.client.car.SearchCarPathRequest;
import com.twtw.backend.domain.path.dto.client.car.SearchCarPathResponse;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.domain.path.service.PathService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    @PostMapping("/search/car")
    public ResponseEntity<SearchCarPathResponse> searchCarPath(
            @RequestBody @Valid SearchCarPathRequest request) {
        return ResponseEntity.ok(pathService.searchCarPath(request));
    }

    @PostMapping("/search/car/cache")
    public ResponseEntity<SearchCarPathResponse> searchCarPathWithCache(
            @RequestBody @Valid SearchCarPathRequest request) {
        return ResponseEntity.ok(pathService.searchCarPathWithCache(request));
    }

    @PostMapping("/search/ped")
    public ResponseEntity<SearchPedPathResponse> searchPedPath(
            @RequestBody @Valid SearchPedPathRequest request) {
        return ResponseEntity.ok(pathService.searchPedPath(request));
    }

    @PostMapping("/search/ped/cache")
    public ResponseEntity<SearchPedPathResponse> searchPedPathWithCache(
            @RequestBody @Valid SearchPedPathRequest request) {
        return ResponseEntity.ok(pathService.searchPedPathWithCache(request));
    }
}
