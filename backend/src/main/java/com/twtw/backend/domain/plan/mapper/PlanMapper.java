package com.twtw.backend.domain.plan.mapper;
import com.twtw.backend.domain.plan.dto.response.SavePlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {
    public SavePlanResponse toPlanResponse (Plan plan){
        return new SavePlanResponse(
                plan.getId(),
                plan.getGroup().getId()
        );
    }
}
