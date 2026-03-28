package com.cms.cmsapp.complaint.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.dto.TimelineDto;
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
import jakarta.persistence.Lob;
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
@Table(name = "timeline")
@ToString(exclude = {"complaint", "user"})
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timelineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Status statusBefore;

    @Enumerated(EnumType.STRING)
    private Status statusAfter;

    @Lob
    private String remarks;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime actionAt;

    public TimelineDto toDto(){
        TimelineDto dto = new TimelineDto();
        dto.setStatus(this.statusAfter);
        dto.setChangedBy(this.user != null ? this.user.getUsername() : "Error: User not found");
        dto.setChangedAt(this.actionAt);
        dto.setRemark(this.remarks);
        return dto;
    }

}
