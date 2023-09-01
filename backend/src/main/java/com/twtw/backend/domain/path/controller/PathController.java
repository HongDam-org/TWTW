package com.twtw.backend.domain.path.controller;

import com.twtw.backend.domain.path.dto.client.SearchPathRequest;
import com.twtw.backend.domain.path.dto.client.SearchPathResponse;
import com.twtw.backend.domain.path.service.PathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathService pathService;

    public PathController(PathService pathService){
        this.pathService = pathService;
    }

    /*response 변경*/
    @PostMapping("/search")
    public ResponseEntity<SearchPathResponse> searchPath(@RequestBody SearchPathRequest request){
        return ResponseEntity.ok(pathService.searchPath(request));
    }
}
