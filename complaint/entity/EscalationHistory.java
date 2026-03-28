package com.cms.cmsapp.complaint.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.dto.EscalationDto;
import com.cms.cmsapp.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "escalation_history")
@ToString(exclude = {"complaint", "escalatedBy"})
public class EscalationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long escalationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EscalationLevel level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status newStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escalated_by_user_id", nullable = false)
    private User escalatedBy;

    private String remarks;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime escalatedAt;

    public EscalationDto toDto() {
        EscalationDto dto = new EscalationDto();
        dto.setId(this.escalationId);
        dto.setLevel(this.level);
        dto.setPreviousStatus(this.previousStatus);
        dto.setNewStatus(this.newStatus);
        dto.setEscalatedBy(this.escalatedBy.getUsername());
        dto.setRemarks(this.remarks);
        dto.setEscalatedAt(this.escalatedAt);
        return dto;
    }

}
