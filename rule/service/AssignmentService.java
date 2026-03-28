package com.cms.cmsapp.rule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.entity.Complaint;

import com.cms.cmsapp.department.entity.Staff;
import com.cms.cmsapp.department.repository.StaffRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssignmentService {

    private final StaffRepo staffRepo;
    private final RuleService ruleService;

    @Transactional
    public void assignComplaint(Complaint complaint) {

        if (!ruleService.getBoolean("AUTO_ASSIGN_ENABLED")) {
            return;
        }

        List<Staff> eligibleStaff = filterEligibleStaff(complaint);

        if (eligibleStaff.isEmpty()) {
            log.warn("No eligible staff found for complaint {}",
                    complaint.getComplaintId());
            return;
        }

        Staff selected = eligibleStaff.isEmpty() ? null : eligibleStaff.get(0);

        if (selected == null) {
            return;
        }

        complaint.setAssignedToUser(selected.getUser());

        log.info("Complaint {} assigned to user {}",
                complaint.getComplaintId(),
                selected.getUser().getUserId());
    }

    @Transactional
    public void reassignComplaint(Complaint complaint) {

        if (!ruleService.getBoolean("REASSIGN_ON_STAFF_INACTIVE")) {
            return;
        }

        assignComplaint(complaint);
    }

    // private List<Staff> filterEligibleStaff(Complaint complaint) {

    // int maxCases = ruleService.getInt("ASSIGN_MAX_ACTIVE_CASES");
    // boolean matchSkill = ruleService.getBoolean("ASSIGN_MATCH_CATEGORY_SKILL");

    // return staffRepo.findByIsActiveTrue().orElseThrow(() -> new
    // RuntimeException("No active staff found"))
    // .stream()
    // .filter(Staff::isAvailable)
    // .filter(staff -> {
    // long activeCases = getActiveCaseCount(staff);
    // return activeCases < maxCases;
    // })
    // .filter(staff -> {
    // return !matchSkill ||
    // staff.getSkills().contains(complaint.getCategory());
    // })
    // .toList();
    // }

    private List<Staff> filterEligibleStaff(Complaint complaint) {

        int maxCases = ruleService.getInt("ASSIGN_MAX_ACTIVE_CASES");
        boolean matchSkill = ruleService.getBoolean("ASSIGN_MATCH_CATEGORY_SKILL");

        List<Status> activeStatuses = List.of(
                Status.OPEN,
                Status.IN_PROGRESS);

        return staffRepo.findEligibleStaffOrdered(
                maxCases,
                activeStatuses,
                complaint.getCategory(),
                matchSkill);
    }

    // private Staff selectLeastLoadedStaff(List<Staff> staffList) {

    //     return staffList.stream()
    //             .min(Comparator.comparingLong(this::getActiveCaseCount))
    //             .orElse(null);
    // }

    // private long getActiveCaseCount(Staff staff) {

    //     return complaintRepo.countByAssignedToUserAndStatusIn(
    //             staff.getUser(),
    //             List.of(Status.OPEN, Status.IN_PROGRESS, Status.REOPENED));
    // }
}
