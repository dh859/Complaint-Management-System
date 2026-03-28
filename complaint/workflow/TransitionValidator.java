package com.cms.cmsapp.complaint.workflow;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Status;

@Component
public class TransitionValidator {

    private static final Map<Status, Set<Status>> TRANSITION_MATRIX = Map.of(
        Status.OPEN, Set.of(Status.IN_PROGRESS, Status.CANCELED),

        Status.IN_PROGRESS, Set.of(Status.RESOLVED, Status.CANCELED),

        Status.RESOLVED, Set.of(Status.CLOSED, Status.REOPENED),

        Status.REOPENED, Set.of(Status.IN_PROGRESS),

        Status.CLOSED, Set.of(),     
        Status.CANCELED, Set.of() 
    );

    public void validate(WorkflowContext context) {

        if (context.getAction() != WorkflowAction.UPDATE_STATUS) {
            return;
        }

        Status current = context.getComplaint().getStatus();
        Status target = context.getTargetStatus();

        if (current == null || target == null) {
            throw new IllegalStateException("Invalid state transition: null status");
        }

        Set<Status> allowedTargets = TRANSITION_MATRIX.getOrDefault(current, Set.of());

        if (!allowedTargets.contains(target)) {
            throw new IllegalStateException(
                "Invalid transition from " + current + " to " + target
            );
        }
    }
}
