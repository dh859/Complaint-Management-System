package com.cms.cmsapp.rule.service;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.complaint.entity.Complaint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EscalationService {

    private final RuleService ruleService;
    private final AssignmentService assignmentService;
    // private final NotificationService notificationService;

    public void evaluateAndEscalate(Complaint complaint) {

        if (!ruleService.getBoolean("ESCALATION_ENABLED")) {
            return;
        }

        EscalationLevel currentLevel =
                complaint.getCurrEscalationLevel() == null
                        ? EscalationLevel.NONE
                        : complaint.getCurrEscalationLevel();

        if (currentLevel.isMaxLevel()) {
            return; 
        }

        EscalationLevel nextLevel = currentLevel.next();

        long elapsedHours = Duration.between(
                complaint.getCreatedAt(),
                LocalDateTime.now()
        ).toHours();

        int thresholdHours = ruleService.getInt(
                "ESCALATION_" + nextLevel.name() + "_HOURS"
        );


        if (elapsedHours >= thresholdHours
                && nextLevel.isHigherThan(currentLevel)) {

            escalate(complaint, currentLevel, nextLevel);
        }
    }

    public void escalate(Complaint complaint,
                          EscalationLevel oldLevel,
                          EscalationLevel newLevel) {

        log.info(
                "Escalating complaint {} from {} to {}",
                complaint.getComplaintId(),
                oldLevel,
                newLevel
        );

        complaint.setCurrEscalationLevel(newLevel);


        if (ruleService.getBoolean("ESCALATION_REASSIGN_ON_BREACH")) {
            assignmentService.reassignComplaint(complaint);
        }

        if (ruleService.getBoolean("ESCALATION_NOTIFY_ENABLED")) {
            // notificationService.notifyEscalation(complaint);
        }

      
    }
}
