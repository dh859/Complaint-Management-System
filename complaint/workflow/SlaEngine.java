package com.cms.cmsapp.complaint.workflow;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.complaint.entity.Complaint;

@Component
public class SlaEngine {

    public void evaluate(Complaint complaint) {

        if (complaint.isOverdue()) {
            complaint.setCurrEscalationLevel(EscalationLevel.LEVEL1);
        }

        if (complaint.getPriority() == Priority.CRITICAL) {
            complaint.setCurrEscalationLevel(EscalationLevel.LEVEL2);
        }
    }
}
