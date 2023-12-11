package com.twtw.backend.domain.plan.dto.response;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlanInfoResponse {
    private UUID planId;
    private UUID placeId;
    private UUID planMakerId;
    private PlaceDetails placeDetails;
    private GroupInfoResponse groupInfo;
    private List<MemberResponse> members;
}
