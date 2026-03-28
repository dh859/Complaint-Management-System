package com.cms.cmsapp.complaint.workflow;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.complaint.service.TimelineService;
import com.cms.cmsapp.rule.service.EscalationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EscalationEngine {

    private final EscalationService escalationService;
    private final TimelineService timelineService;

    public void evaluate(Complaint complaint) {

        EscalationLevel currentLevel =
                complaint.getCurrEscalationLevel();

        EscalationLevel requiredLevel = determineRequiredLevel(complaint, currentLevel);

        if (!requiredLevel.isHigherThan(currentLevel)) {
            return;
        }

        complaint.setCurrEscalationLevel(requiredLevel);

        escalationService.escalate(complaint, currentLevel, requiredLevel);

        timelineService.createTimelineEntry(
                complaint,
                null, 
                complaint.getStatus(),
                complaint.getStatus(),
                "Escalated from " + currentLevel +
                " to " + requiredLevel
        );
    }

    private EscalationLevel determineRequiredLevel(
            Complaint complaint,
            EscalationLevel currentLevel
    ) {

        if (complaint.getPriority() == Priority.CRITICAL
                && currentLevel.isLowerThan(EscalationLevel.LEVEL2)) {

            return EscalationLevel.LEVEL2;
        }

        if (complaint.isOverdue() && !currentLevel.isMaxLevel()) {
            return currentLevel.next();
        }

        return currentLevel;
    }
}
