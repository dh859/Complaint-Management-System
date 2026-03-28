package com.cms.cmsapp.complaint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.user.entity.User;

@Repository
public interface ComplaintRepo extends JpaRepository<Complaint, Long> {

	public Optional<List<Complaint>> findByRaisedByUser(User user);

	public Optional<Complaint> findByComplaintIdAndRaisedByUser(Long complaintId, User user);

	public Optional<List<Complaint>> findByAssignedToUser(User user);

	public Optional<List<Complaint>> findByAssignedToUserIsNullAndStatusIn(List<Status> statuses);

	public Optional<Complaint> findByComplaintIdAndAssignedToUser(Long complaintId, User user);

	public Optional<List<Complaint>> findByForwardedToDepartment(ExternalDepartment department);

	public Optional<Complaint> findByComplaintIdAndForwardedToDepartment(Long complaintId, ExternalDepartment department);

	public long countByStatus(Status status);

	public long countByCategory(Category category);

	public long countByCategoryAndStatus(Category category, Status status);

	public long countByAssignedToUser(User user);

	public long countByAssignedToUserAndStatus(User user, Status status);

	public long countByRaisedByUser(User user);

	public long countByRaisedByUserAndStatus(User user, Status status);

	public long countByForwardedToDepartment(ExternalDepartment department);

	public long countByForwardedToDepartmentAndStatus(ExternalDepartment department, Status status);

	Optional<List<Complaint>> findByStatusIn(List<Status> statuses);

	long countByAssignedToUserAndStatusIn(User user, List<Status> statuses);
}
