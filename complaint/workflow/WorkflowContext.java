package com.cms.cmsapp.complaint.workflow;

import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.user.entity.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkflowContext {

    private Complaint complaint;
    private User actingUser;
    private WorkflowAction action;

    private Status targetStatus;
    private User assignedUser;
    private ExternalDepartment department;
    private String remarks;

}
