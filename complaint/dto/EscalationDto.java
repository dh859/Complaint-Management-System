package com.cms.cmsapp.complaint.dto;

import java.time.LocalDateTime;
import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscalationDto {
    private Long id;
    private EscalationLevel level;

    private Status previousStatus;

    private Status newStatus;

    private String escalatedBy;

    private String remarks;

    private LocalDateTime escalatedAt;
}
