package com.cms.cmsapp.complaint.workflow;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.complaint.repository.ComplaintRepo;
import com.cms.cmsapp.complaint.service.TimelineService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkflowEngine {

    private final TransitionValidator transitionValidator;
    private final RoleValidator roleValidator;
    private final SlaEngine slaEngine;
    private final EscalationEngine escalationEngine;
    private final ComplaintRepo complaintRepo;
    private final TimelineService timelineService;

    @Transactional
    public Complaint process(WorkflowContext context) {

        Complaint complaint = context.getComplaint();

        Status statusBefore = complaint.getStatus();

        // 1 Validate role
        roleValidator.validate(context);

        // 2 Validate transition rules
        transitionValidator.validate(context);

        // 3 Apply action
        applyAction(context);

        // 4 SLA check
        slaEngine.evaluate(complaint);

        // 5 Escalation check
        escalationEngine.evaluate(complaint);

        // 6 Timeline creation
        timelineService.createTimelineEntry(
                complaint,
                context.getActingUser(),
                statusBefore,
                context.getComplaint().getStatus(),
                context.getRemarks());

        return complaintRepo.save(complaint);
    }

    private void applyAction(WorkflowContext context) {

        Complaint complaint = context.getComplaint();

        switch (context.getAction()) {

            case CREATE -> {
                complaint.setStatus(Status.OPEN);
                complaint.setCurrEscalationLevel(EscalationLevel.NONE);
            }
            case UPDATE_STATUS ->
                complaint.setStatus(context.getTargetStatus());

            case ASSIGN -> {
                complaint.setAssignedToUser(context.getAssignedUser());
                complaint.setStatus(Status.ASSIGNED);
            }

            case FORWARD -> {
                complaint.setForwardedToDepartment(context.getDepartment());
                complaint.setStatus(Status.FORWARDED);
            }

            case REOPEN -> {
                    complaint.setStatus(Status.REOPENED);
                    complaint.setCurrEscalationLevel(EscalationLevel.NONE);
            }
            default ->
                throw new IllegalArgumentException("Unsupported workflow action");
        }
    }
}
