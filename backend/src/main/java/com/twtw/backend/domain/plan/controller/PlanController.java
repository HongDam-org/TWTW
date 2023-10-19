package com.twtw.backend.domain.plan.controller;

import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.SavePlanResponse;
import com.twtw.backend.domain.plan.service.PlanService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping("search/destination")
    public ResponseEntity<PlanDestinationResponse> searchPlanDestination(
            @ModelAttribute final SearchDestinationRequest request) {
        return ResponseEntity.ok(planService.searchPlanDestination(request));
    }

    @PostMapping()
    public ResponseEntity<SavePlanResponse> savePlan(@RequestBody SavePlanRequest request){
        return ResponseEntity.ok(planService.savePlan(request));
    }
}
