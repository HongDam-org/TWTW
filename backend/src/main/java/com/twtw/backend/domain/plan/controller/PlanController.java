package com.twtw.backend.domain.plan.controller;

import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.service.PlanService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<PlanResponse> savePlan(@RequestBody SavePlanRequest request) {
        return ResponseEntity.ok(planService.savePlan(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanInfoResponse> getPlanById(@PathVariable UUID id){
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @DeleteMapping("/{id}")
    public void deletePlanById(@PathVariable UUID id){

    }
}
