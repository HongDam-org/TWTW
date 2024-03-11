package com.twtw.backend.domain.plan.controller;

import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.request.PlanMemberRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.request.UpdatePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.service.PlanService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<PlanInfoResponse> getPlanById(@PathVariable UUID id) {
        return ResponseEntity.ok(planService.getPlanById(id));
    }

    @GetMapping("/{id}/cache")
    public ResponseEntity<PlanInfoResponse> getPlanByIdWithCache(@PathVariable UUID id) {
        return ResponseEntity.ok(planService.getPlanByIdWithCache(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanById(@PathVariable UUID id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<PlanResponse> invitePlan(@RequestBody PlanMemberRequest request) {
        return ResponseEntity.ok(planService.invitePlan(request));
    }

    @DeleteMapping("/invite")
    public ResponseEntity<Void> deleteInvite(@RequestBody PlanMemberRequest request) {
        planService.deleteInvite(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinPlan(@RequestBody PlanMemberRequest request) {
        planService.joinPlan(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/out")
    public ResponseEntity<Void> outPlan(@RequestBody PlanMemberRequest request) {
        planService.outPlan(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PlanInfoResponse>> getPlans() {
        return ResponseEntity.ok(planService.getPlans());
    }

    @GetMapping("/cache")
    public ResponseEntity<List<PlanInfoResponse>> getPlansWithCache() {
        return ResponseEntity.ok(planService.getPlansWithCache());
    }

    @GetMapping("group/{groupId}")
    public ResponseEntity<List<PlanInfoResponse>> getPlansByGroupId(
            @PathVariable final UUID groupId) {
        return ResponseEntity.ok(planService.getPlansByGroupId(groupId));
    }

    @GetMapping("group/{groupUd}/cache")
    public ResponseEntity<List<PlanInfoResponse>> getPlansByGroupIdWithCache(
            @PathVariable final UUID groupId) {
        return ResponseEntity.ok(planService.getPlansByGroupIdWithCache(groupId));
    }

    @PostMapping("update")
    public ResponseEntity<Void> updatePlan(@RequestBody final UpdatePlanRequest updatePlanRequest) {
        planService.updatePlan(updatePlanRequest);
        return ResponseEntity.noContent().build();
    }
}
