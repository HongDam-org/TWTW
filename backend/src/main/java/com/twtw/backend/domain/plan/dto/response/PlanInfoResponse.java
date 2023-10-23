package com.twtw.backend.domain.plan.dto.response;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanInfoResponse {
    private UUID planId;
    private UUID placeId;
    private PlaceDetails placeDetail;
    private GroupInfoResponse groupInfoResponse;
    private List<MemberResponse> memberList;
}
