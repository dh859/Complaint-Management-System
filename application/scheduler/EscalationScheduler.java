package com.cms.cmsapp.application.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.complaint.repository.ComplaintRepo;
import com.cms.cmsapp.rule.service.EscalationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EscalationScheduler {

    private final ComplaintRepo complaintRepository;
    private final EscalationService escalationService;

 
    @Scheduled(fixedDelayString = "${cms.escalation.interval-ms:900000}")
    public void runEscalationCheck() {

        log.debug("Starting escalation scheduler");

        List<Complaint> activeComplaints =
                complaintRepository.findByStatusIn(
                        List.of(
                                Status.OPEN,
                                Status.IN_PROGRESS
                        )
                ).orElseThrow(()-> new ResourceNotFoundException("No active complaints found"));

        for (Complaint complaint : activeComplaints) {
            try {
                escalationService.evaluateAndEscalate(complaint);
            } catch (Exception ex) {
                log.error(
                        "Escalation failed for complaint {}",
                        complaint.getComplaintId(),
                        ex
                );
            }
        }

        log.debug("Escalation scheduler completed");
    }
}
