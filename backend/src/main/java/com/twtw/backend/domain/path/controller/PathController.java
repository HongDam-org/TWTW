package com.twtw.backend.domain.path.controller;

import com.twtw.backend.domain.path.dto.client.car.SearchCarPathRequest;
import com.twtw.backend.domain.path.dto.client.car.SearchCarPathResponse;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.domain.path.service.PathService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;

    public PathController(PathService pathService) {
        this.pathService = pathService;
    }

    /*response 변경*/
    @PostMapping("/search/car")
    public ResponseEntity<SearchCarPathResponse> searchCarPath(
            @RequestBody SearchCarPathRequest request) {
        return ResponseEntity.ok(pathService.searchCarPath(request));
    }

    @PostMapping("search/ped")
    public ResponseEntity<SearchPedPathResponse> searchPedPath(
            @RequestBody SearchPedPathRequest request) {
        return ResponseEntity.ok(pathService.searchPedPath(request));
    }
}
