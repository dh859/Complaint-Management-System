package com.cms.cmsapp.complaint.workflow;

import org.springframework.stereotype.Component;

import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.common.exceptions.AccessDeniedException;
import com.cms.cmsapp.user.entity.User;


@Component
public class RoleValidator {

    public void validate(WorkflowContext context) {

        User user = context.getActingUser();
        WorkflowAction action = context.getAction();

        switch (action) {

            case UPDATE_STATUS -> validateStatusChange(user, context);

            case ASSIGN -> {
                if (!(user.getRole() == Role.ADMIN ||
                      user.getRole() == Role.MANAGER))
                    throw new AccessDeniedException("Not allowed to assign");
            }

            case REOPEN -> {
                if (user.getRole() != Role.ADMIN)
                    throw new AccessDeniedException("Only Admin can reopen");
            }
        }
    }

    private void validateStatusChange(User user, WorkflowContext context) {

        Status target = context.getTargetStatus();

        if (target == Status.IN_PROGRESS &&
            user.getRole() != Role.MANAGER)
            throw new AccessDeniedException("Only Manager can move to IN_PROGRESS");

        if (target == Status.RESOLVED &&
            user.getRole() != Role.AGENT)
            throw new AccessDeniedException("Only Agent can resolve");

        if (target == Status.CLOSED &&
            !(user.getRole() == Role.ADMIN ||
              user.getRole() == Role.MANAGER))
            throw new AccessDeniedException("Only Manager/Admin can close");
    }
}
