package com.twtw.backend.domain.plan.controller;

import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.request.PlanMemberRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.service.PlanService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<PlanInfoResponse> getPlanById(@PathVariable UUID id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanById(@PathVariable UUID id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join")
    public ResponseEntity<PlanResponse> joinPlan(@RequestBody PlanMemberRequest request) {
        return ResponseEntity.ok(planService.joinPlan(request));
    }

    @PostMapping("/out")
    public ResponseEntity<Void> outPlan(@RequestBody PlanMemberRequest request) {
        planService.outPlan(request);
        return ResponseEntity.noContent().build();
    }
}
