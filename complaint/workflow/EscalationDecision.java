package com.cms.cmsapp.complaint.workflow;

import com.cms.cmsapp.common.Enums.EscalationLevel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EscalationDecision {

    private final boolean shouldEscalate;
    private final EscalationLevel targetLevel;
    private final String reason;

    public static EscalationDecision escalate(EscalationLevel level, String reason) {
        return new EscalationDecision(true, level, reason);
    }

    public static EscalationDecision noEscalation() {
        return new EscalationDecision(false, null, null);
    }
}
