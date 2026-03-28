package com.cms.cmsapp.application.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.complaint.repository.ComplaintRepo;
import com.cms.cmsapp.rule.service.AssignmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssignmentSheduler {
     private final ComplaintRepo complaintRepository;
     private final AssignmentService assignmentService;

 
    @Scheduled(fixedDelayString = "${cms.assignment.interval-ms:900000}")
    public void runAutoAssignment() {

        log.debug("Starting Assignment scheduler");

        List<Complaint> unassignedComplaints =
                complaintRepository.findByStatusIn(List.of(Status.OPEN,Status.REOPENED)).orElseThrow(()-> new ResourceNotFoundException("No unassigned complaints found"));

        for (Complaint complaint : unassignedComplaints) {
            try {
                assignmentService.assignComplaint(complaint);
            } catch (Exception ex) {
                log.error(
                        "Assignment failed for complaint {}",
                        complaint.getComplaintId(),
                        ex
                );
            }
        }

        log.debug("Assignment scheduler completed");
    }
}
