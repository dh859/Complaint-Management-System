package com.cms.cmsapp.reporting.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.ReportType;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.common.exceptions.InvalidOperationException;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.common.utils.FileExporter.ReportPdfExporter;
import com.cms.cmsapp.complaint.repository.ComplaintRepo;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.reporting.dto.ReportDto;
import com.cms.cmsapp.reporting.entity.Report;
import com.cms.cmsapp.reporting.repository.ReportRepo;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ReportService {

    @Autowired
    private ReportRepo reportRepo;

    @Autowired
    private ComplaintRepo complaintRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ReportPdfExporter reportPdfExporter;

    @Autowired
    private ObjectMapper objectMapper;


    private Map<String, Long> getStatusCounts() {
        Map<String, Long> statusCounts = new HashMap<>();

        statusCounts.put("totalComplaints", complaintRepo.count());
        statusCounts.put("open", complaintRepo.countByStatus(Status.OPEN));
        statusCounts.put("assigned", complaintRepo.countByStatus(Status.ASSIGNED));
        statusCounts.put("inProgress", complaintRepo.countByStatus(Status.IN_PROGRESS));
        statusCounts.put("resolved", complaintRepo.countByStatus(Status.RESOLVED));
        statusCounts.put("forwarded", complaintRepo.countByStatus(Status.FORWARDED));
        statusCounts.put("reopened", complaintRepo.countByStatus(Status.REOPENED));
        statusCounts.put("closed", complaintRepo.countByStatus(Status.CLOSED));
        statusCounts.put("canceled", complaintRepo.countByStatus(Status.CANCELED));

        return statusCounts;
    }

    private Report saveReport(User user, ReportType reportType, Map<String, Object> data) {
        try {
            String json = objectMapper.writeValueAsString(data);

            Report report = new Report();
            report.setGeneratedByUser(user);
            report.setReportType(reportType);
            report.setContent(json);

            return reportRepo.save(report);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate report", e);
        }
    }

    public Report generateComplaintSummaryReport(User user) {

        Map<String, Object> data = new HashMap<>();
        data.put("reportTitle", "Complaint Summary Report");
        data.put("generatedOn", LocalDateTime.now());
        data.putAll(getStatusCounts());

        return saveReport(user, ReportType.SUMMARY_REPORT, data);
    }


    public Report generateCategoryReport(User user, Category category) {

        Map<String, Object> data = new HashMap<>();

        data.put("reportTitle", "Category-wise Complaint Report");
        data.put("generatedOn", LocalDateTime.now());
        data.put("category", category.name());

        data.put("totalComplaints", complaintRepo.countByCategory(category));
        data.put("open", complaintRepo.countByCategoryAndStatus(category, Status.OPEN));
        data.put("assigned", complaintRepo.countByCategoryAndStatus(category, Status.ASSIGNED));
        data.put("inProgress", complaintRepo.countByCategoryAndStatus(category, Status.IN_PROGRESS));
        data.put("resolved", complaintRepo.countByCategoryAndStatus(category, Status.RESOLVED));
        data.put("forwarded", complaintRepo.countByCategoryAndStatus(category, Status.FORWARDED));
        data.put("reopened", complaintRepo.countByCategoryAndStatus(category, Status.REOPENED));
        data.put("closed", complaintRepo.countByCategoryAndStatus(category, Status.CLOSED));
        data.put("canceled", complaintRepo.countByCategoryAndStatus(category, Status.CANCELED));

        return saveReport(user, ReportType.CATEGORY_REPORT, data);
    }

    public Report generateAgentReport(User agent, User generatedBy) {

        Map<String, Object> data = new HashMap<>();

        data.put("reportTitle", "Agent Performance Report");
        data.put("generatedOn", LocalDateTime.now());
        data.put("agent", agent.getUsername());

        data.put("totalAssigned", complaintRepo.countByAssignedToUser(agent));
        data.put("open", complaintRepo.countByAssignedToUserAndStatus(agent, Status.OPEN));
        data.put("assigned", complaintRepo.countByAssignedToUserAndStatus(agent, Status.ASSIGNED));
        data.put("inProgress", complaintRepo.countByAssignedToUserAndStatus(agent, Status.IN_PROGRESS));
        data.put("resolved", complaintRepo.countByAssignedToUserAndStatus(agent, Status.RESOLVED));
        data.put("forwarded", complaintRepo.countByAssignedToUserAndStatus(agent, Status.FORWARDED));
        data.put("reopened", complaintRepo.countByAssignedToUserAndStatus(agent, Status.REOPENED));
        data.put("closed", complaintRepo.countByAssignedToUserAndStatus(agent, Status.CLOSED));
        data.put("canceled", complaintRepo.countByAssignedToUserAndStatus(agent, Status.CANCELED));

        return saveReport(generatedBy, ReportType.AGENT_REPORT, data);
    }


    public Report generateSystemReport(User generatedBy) {

        Map<String, Object> data = new HashMap<>();

        data.put("reportTitle", "System Report");
        data.put("generatedOn", LocalDateTime.now());

        data.put("complaints", getStatusCounts());

        Map<String, Object> users = new HashMap<>();
        users.put("total", userRepo.count());
        users.put("admins", userRepo.countByRole(Role.ADMIN));
        users.put("managers", userRepo.countByRole(Role.MANAGER));
        users.put("agents", userRepo.countByRole(Role.AGENT));
        users.put("users", userRepo.countByRole(Role.USER));
        data.put("users", users);

        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> system = new HashMap<>();
        system.put("totalMemoryMB", runtime.totalMemory() / (1024 * 1024));
        system.put("usedMemoryMB",
                (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
        system.put("availableProcessors", runtime.availableProcessors());
        system.put("activeThreads", Thread.activeCount());
        data.put("system", system);

        return saveReport(generatedBy, ReportType.SYSTEM_REPORT, data);
    }


    public Report generateDepartmentReport(User user, ExternalDepartment department) {

        Map<String, Object> data = new HashMap<>();

        data.put("reportTitle", "Department Performance Report");
        data.put("generatedOn", LocalDateTime.now());
        data.put("department", department.getName());

        data.put("totalAssigned", complaintRepo.countByForwardedToDepartment(department));
        data.put("open", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.OPEN));
        data.put("assigned", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.ASSIGNED));
        data.put("inProgress", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.IN_PROGRESS));
        data.put("resolved", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.RESOLVED));
        data.put("forwarded", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.FORWARDED));
        data.put("reopened", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.REOPENED));
        data.put("closed", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.CLOSED));
        data.put("canceled", complaintRepo.countByForwardedToDepartmentAndStatus(department, Status.CANCELED));

        return saveReport(user, ReportType.DEPARTMENT_REPORT, data);
    }


    public List<ReportDto> getReportsByUser(User user) {
        return reportRepo.findByGeneratedByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No reports found for user id: " + user.getUserId()))
                .stream()
                .map(Report::toReportDto)
                .toList();
    }

    public List<ReportDto> getAllReports() {
        return reportRepo.findAll()
                .stream()
                .map(Report::toReportDto)
                .toList();
    }

    public Report getReportById(Long reportId) {
        return reportRepo.findByReportId(reportId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Report not found by Id : " + reportId));
    }

    public List<ReportDto> getReportsByType(String reportType) {
        return reportRepo.findByReportType(ReportType.fromString(reportType))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No reports found of type: " + reportType))
                .stream()
                .map(Report::toReportDto)
                .toList();
    }

    public String deleteReport(Long reportId) {
        Report report = getReportById(reportId);
        reportRepo.delete(report);
        return "Report deleted successfully.";
    }

    public byte[] getReportPdf(Long reportId) {
        try {
            Report report = getReportById(reportId);
            return reportPdfExporter.export(report);
        } catch (Exception e) {
            throw new InvalidOperationException("Error Generating Pdf: " + e.getMessage());
        }
    }
}
