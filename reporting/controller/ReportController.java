package com.cms.cmsapp.reporting.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.exceptions.InvalidOperationException;
import com.cms.cmsapp.reporting.dto.ReportDto;
import com.cms.cmsapp.reporting.entity.Report;
import com.cms.cmsapp.reporting.service.ReportService;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.service.UserService;

@RestController
@RequestMapping("/reports")
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public class ReportController {

    private final ReportService reportService;
    private final UserService userService;

    public ReportController(ReportService reportService, UserService userService) {
        this.reportService = reportService;
        this.userService = userService;
    }

    @GetMapping("/complaint-summary")
    public ReportDto getComplaintSummaryReport(@AuthenticationPrincipal UserDetailsDto userDetails) {
        User currentUser = userService.getById(userDetails.getUserId());
        Report report = reportService.generateComplaintSummaryReport(currentUser);
        return report.toReportDto();
    }

    @GetMapping("/category-summary")
    public ReportDto getCategorySummaryReport(@AuthenticationPrincipal UserDetailsDto userDetails,
            @RequestBody Category category) {
        User currentUser = userService.getById(userDetails.getUserId());
        Report report = reportService.generateCategoryReport(currentUser, category);
        return report.toReportDto();
    }

    @GetMapping("/agent-performance")
    public ReportDto getAgentPerformanceReport(@AuthenticationPrincipal UserDetailsDto userDetails,
            @RequestBody Long agentId) {
        User currentUser = userService.getById(userDetails.getUserId());
        User agent = userService.getById(agentId);
        Report report = reportService.generateAgentReport(agent, currentUser);
        return report.toReportDto();
    }

    @GetMapping("/system-report")
    @PreAuthorize("hasRole('ADMIN')")
    public ReportDto getSystemReport(@AuthenticationPrincipal UserDetailsDto userDetails) {
        User currentUser = userService.getById(userDetails.getUserId());
        Report report = reportService.generateSystemReport(currentUser);
        return report.toReportDto();
    }

    @GetMapping("/reportId/{reportId}")
    public ReportDto getReportById(@AuthenticationPrincipal UserDetailsDto userDetails, @PathVariable Long reportId) {
        Report report = reportService.getReportById(reportId);
        if (report.getGeneratedByUser().getUserId() != userDetails.getUserId()) {
            throw new InvalidOperationException("Access Denied for this Report");
        }
        return report.toReportDto();
    }

    @GetMapping("/admin/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ReportDto getReportByIdAdmin(@PathVariable Long reportId) {
        Report report = reportService.getReportById(reportId);
        return report.toReportDto();
    }

    @GetMapping("/report-type/{reportType}")
    public List<ReportDto> getReportsByType(@AuthenticationPrincipal UserDetailsDto userDetails,
            @PathVariable String reportType) {
        return reportService.getReportsByType(reportType);
    }

    @GetMapping("/all-reports")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReportDto> getAllReports(@AuthenticationPrincipal UserDetailsDto userDetails) {
        return reportService.getAllReports();
    }

    @GetMapping("/my-reports")
    public List<ReportDto> getReportsByUser(@AuthenticationPrincipal UserDetailsDto userDetails) {
        User currentUser = userService.getById(userDetails.getUserId());
        return reportService.getReportsByUser(currentUser);
    }

    @DeleteMapping("/delete-report/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteReport(@AuthenticationPrincipal UserDetailsDto userDetails, @PathVariable Long reportId) {
        return reportService.deleteReport(reportId);
    }

    @GetMapping("/user-reports/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ReportDto> getReportsByUserId(@AuthenticationPrincipal UserDetailsDto userDetails,@PathVariable Long userId) {
        User user = userService.getById(userId);
        return reportService.getReportsByUser(user);
    }

    @GetMapping("/{id}/view")
    public ResponseEntity<byte[]> viewReport(@PathVariable Long id) {

        byte[] pdfBytes = reportService.getReportPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=report-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {

        byte[] pdfBytes = reportService.getReportPdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
