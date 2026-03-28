package com.cms.cmsapp.complaint.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.EmailType;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.complaint.dto.ComplaintDto;
import com.cms.cmsapp.complaint.dto.CreateComplaintDto;
import com.cms.cmsapp.complaint.dto.ShortComplaintDto;
import com.cms.cmsapp.complaint.dto.UserComplaintDto;
import com.cms.cmsapp.complaint.entity.Attatchment;
import com.cms.cmsapp.complaint.entity.Complaint;
import com.cms.cmsapp.complaint.repository.ComplaintRepo;
import com.cms.cmsapp.complaint.workflow.WorkflowAction;
import com.cms.cmsapp.complaint.workflow.WorkflowContext;
import com.cms.cmsapp.complaint.workflow.WorkflowEngine;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.repository.DepartmentRepo;
import com.cms.cmsapp.file.FileService;
import com.cms.cmsapp.notification.aop.SendMail;
import com.cms.cmsapp.rule.service.CategorisationService;
import com.cms.cmsapp.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintService {

	private final ComplaintRepo complaintRepo;
	private final DepartmentRepo departmentRepo;
	private final FileService fileService;
	private final CategorisationService categorisationService;
	private final WorkflowEngine workflowEngine;

	@SendMail(type = EmailType.COMPLAINT_SUBMISSION)
	public Complaint create(CreateComplaintDto comp, User user) {

		Complaint complaint = new Complaint();

		List<Attatchment> files = fileService.saveFiles(comp.getFiles());

		complaint.setSubject(comp.getSubject());
		complaint.setDescription(comp.getDescription());
		complaint.setCategory(comp.getCategory());
		complaint.setRaisedByUser(user);
		complaint.setPriority(
				comp.getPriority() == null
						? Priority.MEDIUM
						: comp.getPriority());

		files.forEach(f -> f.setComplaint(complaint));
		complaint.setAttatchments(files);

		Complaint savedComplaint = complaintRepo.save(complaint);

		WorkflowContext context = WorkflowContext.builder()
				.complaint(savedComplaint)
				.action(WorkflowAction.CREATE)
				.actingUser(user)
				.remarks("Complaint created")
				.build();

		Complaint processed = workflowEngine.process(context);

		categorisationService.categorise(processed);

		return processed;
	}

	@SendMail(type = EmailType.STATUS_UPDATE)
	public Complaint updateComplaintStatus(
			Long complaintId,
			Status targetStatus,
			User performedBy,
			String remarks) {

		Complaint complaint = getByIdOrThrow(complaintId);

		WorkflowContext context = WorkflowContext.builder()
				.complaint(complaint)
				.action(WorkflowAction.UPDATE_STATUS)
				.targetStatus(targetStatus)
				.actingUser(performedBy)
				.remarks(remarks)
				.build();

		return workflowEngine.process(context);
	}

	@SendMail(type = EmailType.COMPLAINT_ASSIGNMENT)
	public Complaint assignComplaint(
			Long complaintId,
			User assignedUser,
			User performedBy,
			String remarks) {

		Complaint complaint = getByIdOrThrow(complaintId);

		WorkflowContext context = WorkflowContext.builder()
				.complaint(complaint)
				.action(WorkflowAction.ASSIGN)
				.assignedUser(assignedUser)
				.actingUser(performedBy)
				.remarks(remarks)
				.build();

		return workflowEngine.process(context);
	}

	@SendMail(type = EmailType.FORWARD_TO_DEPARTMENT)
	public Complaint forwardComplaint(
			Long complaintId,
			Long departmentId,
			User performedBy,
			String remarks) {

		Complaint complaint = getByIdOrThrow(complaintId);

		ExternalDepartment department = departmentRepo.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

		WorkflowContext context = WorkflowContext.builder()
				.complaint(complaint)
				.action(WorkflowAction.FORWARD)
				.department(department)
				.actingUser(performedBy)
				.remarks(remarks)
				.build();

		return workflowEngine.process(context);
	}

	
	public Complaint reopenComplaint(
			Long complaintId,
			User performedBy,
			String remarks) {

		Complaint complaint = getByIdOrThrow(complaintId);

		WorkflowContext context = WorkflowContext.builder()
				.complaint(complaint)
				.action(WorkflowAction.REOPEN)
				.actingUser(performedBy)
				.remarks(remarks)
				.build();

		return workflowEngine.process(context);
	}

	public Complaint getByIdOrThrow(Long id) {
		return complaintRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Complaint not found with id: " + id));
	}

	public ComplaintDto getComplaintById(Long id) {
		return getByIdOrThrow(id).toComplaintDto();
	}

	public List<ShortComplaintDto> getComplaintsByDepartment(ExternalDepartment department) {
		return complaintRepo.findByForwardedToDepartment(department)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found by Department"))
				.stream()
				.map(Complaint::toShortComplaintDto)
				.toList();
	}

	public List<ShortComplaintDto> getComplaintsByAssignedAgent(User agent) {
		return complaintRepo.findByAssignedToUser(agent)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found by Assigned Agent"))
				.stream()
				.map(Complaint::toShortComplaintDto)
				.toList();
	}

	public ComplaintDto getComplaintByIdAndDepartment(Long id, ExternalDepartment department) {
		return complaintRepo.findByComplaintIdAndForwardedToDepartment(id, department)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Complaint not found with id: " + id + " in your department"))
				.toComplaintDto();
	}

	public ComplaintDto getComplaintByIdAndAssignedAgent(Long id, User agent) {
		return complaintRepo.findByComplaintIdAndAssignedToUser(id, agent)
				.orElseThrow(
						() -> new ResourceNotFoundException("Complaint not found with id: " + id + " assigned to you"))
				.toComplaintDto();
	}

	public List<ShortComplaintDto> getAllComplaints() {
		return complaintRepo.findAll()
				.stream()
				.map(Complaint::toShortComplaintDto)
				.toList();
	}

	public List<ShortComplaintDto> getComplaintsByUser(User user) {
		return complaintRepo.findByRaisedByUser(user)
				.orElseThrow(() -> new ResourceNotFoundException("No complaints found for user: " + user.getUsername()))
				.stream()
				.map(Complaint::toShortComplaintDto)
				.toList();
	}

	public UserComplaintDto getComplaintByComplaintIdAndUser(Long id, User user) {
		return complaintRepo.findByComplaintIdAndRaisedByUser(id, user)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Complaint not found with id: " + id + " for user: " + user.getUsername()))
				.toUserComplaintDto();
	}

	public void deleteComplaint(Long id) {
		complaintRepo.delete(getByIdOrThrow(id));
	}

	public long countAllComplaints() {
		return complaintRepo.count();
	}

	public long countComplaintsByStatus(Status status) {
		return complaintRepo.countByStatus(status);
	}

	public long countComplaintsByCategory(Category category) {
		return complaintRepo.countByCategory(category);
	}

	public long countComplaintsByCategoryAndStatus(Category category, Status status) {
		return complaintRepo.countByCategoryAndStatus(category, status);
	}

	public long countComplaintsByAssignedAgent(User user) {
		return complaintRepo.countByAssignedToUser(user);
	}

	public long countComplaintsByAssignedAgentAndStatus(User user, Status status) {
		return complaintRepo.countByAssignedToUserAndStatus(user, status);
	}

	public long countComplaintsByRaisedByUser(User user) {
		return complaintRepo.countByRaisedByUser(user);
	}

	public long countComplaintsByRaisedByUserAndStatus(User user, Status status) {
		return complaintRepo.countByRaisedByUserAndStatus(user, status);
	}

}
