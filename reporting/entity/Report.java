package com.cms.cmsapp.reporting.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cms.cmsapp.common.Enums.ReportType;
import com.cms.cmsapp.reporting.dto.ReportDto;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String reportTitle;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "generated_by_user_id")
    private User generatedByUser;

    public ReportDto toReportDto() {
        ReportDto dto = new ReportDto();
        dto.setReportId(this.reportId);
        dto.setReportTitle(this.reportTitle);
        dto.setType(this.reportType.name());
        dto.setCreatedAt(this.createdAt);
        dto.setGeneratedByUserId(getGeneratedByUser().getUserId());
        dto.setGeneratedByUsername(generatedByUser.getUsername());
        dto.setGeneratedByUserFullname(generatedByUser.getFullname());
        return dto;
    }
}
