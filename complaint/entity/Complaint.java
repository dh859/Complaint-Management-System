package com.cms.cmsapp.complaint.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.EscalationLevel;
import com.cms.cmsapp.common.Enums.Priority;
import com.cms.cmsapp.common.Enums.Status;
import com.cms.cmsapp.complaint.dto.ComplaintDto;
import com.cms.cmsapp.complaint.dto.EscalationDto;
import com.cms.cmsapp.complaint.dto.ShortComplaintDto;
import com.cms.cmsapp.complaint.dto.TimelineDto;
import com.cms.cmsapp.complaint.dto.UserComplaintDto;
import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.file.FileDto;
import com.cms.cmsapp.user.entity.User;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "complaints")
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long complaintId;

	@Column(nullable = false, length = 255)
	private String subject;

	@Lob
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.OPEN;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Priority priority;

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "raised_by_user_id", nullable = false)
	private User raisedByUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assigned_to_user_id")
	private User assignedToUser;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "external_department_id")
	private ExternalDepartment forwardedToDepartment;

	private EscalationLevel currEscalationLevel = EscalationLevel.NONE;

	private LocalDateTime slaDeadLine;

	@OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attatchment> attatchments = new ArrayList<>();

	@OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EscalationHistory> escalationHistories = new ArrayList<>();

	@OneToMany(mappedBy = "complaint", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Timeline> timelineEvents = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
		status = Status.OPEN;
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}

	public boolean isOverdue() {

		if (slaDeadLine == null) {
			return false;
		}
		if (status == Status.RESOLVED || status == Status.CLOSED) {
			return false;
		}

		return LocalDateTime.now().isAfter(slaDeadLine);
	}

	public ShortComplaintDto toShortComplaintDto() {
		ShortComplaintDto dto = new ShortComplaintDto();
		dto.setComplaintId(complaintId);
		dto.setSubject(subject);
		dto.setDescription(description);
		dto.setStatus(status);
		dto.setPriority(priority.name());
		dto.setCategory(category);
		dto.setCreatedAt(createdAt);
		dto.setRaisedByUser_Name(raisedByUser != null ? raisedByUser.getFullname() : null);
		dto.setAssignedToUser_Name(assignedToUser != null ? assignedToUser.getFullname() : null);
		dto.setDepartment_Name(forwardedToDepartment != null ? forwardedToDepartment.getName() : null);
		return dto;
	}

	public ComplaintDto toComplaintDto() {
		ComplaintDto dto = new ComplaintDto();

		dto.setId(complaintId);
		dto.setSubject(subject);
		dto.setDescription(description);
		dto.setStatus(status);
		dto.setCategory(category);
		dto.setPriority(priority);
		dto.setCreatedAt(createdAt);
		dto.setUpdatedAt(updatedAt);

		dto.setCreatedByName(
				raisedByUser != null ? raisedByUser.getFullname() : null);

		dto.setAssignedStaffName(
				assignedToUser != null ? assignedToUser.getFullname() : null);

		dto.setDepartmentName(
				forwardedToDepartment != null ? forwardedToDepartment.getName() : null);

		dto.setOverdue(isOverdue());
		dto.setEscalationLevel(currEscalationLevel);

		List<FileDto> fileDtos = (attatchments == null)
				? Collections.emptyList()
				: attatchments.stream()
						.map(Attatchment::toFileDto)
						.toList();

		dto.setAttatchments(fileDtos);

		List<TimelineDto> timelineDtos = (timelineEvents == null)
				? Collections.emptyList()
				: timelineEvents.stream()
						.map(Timeline::toDto)
						.toList();

		dto.setTimelines(timelineDtos);

		List<EscalationDto> escalationDtos = (escalationHistories == null)
				? Collections.emptyList()
				: escalationHistories.stream()
						.map(EscalationHistory::toDto)
						.toList();

		dto.setEscalations(escalationDtos);

		return dto;
	}

	public UserComplaintDto toUserComplaintDto() {
		UserComplaintDto dto = new UserComplaintDto();

		dto.setId(complaintId);
		dto.setSubject(subject);
		dto.setDescription(description);
		dto.setStatus(status);
		dto.setCategory(category);
		dto.setPriority(priority);
		dto.setCreatedAt(createdAt);
		dto.setUpdatedAt(updatedAt);

		List<FileDto> fileDtos = (attatchments == null)
				? Collections.emptyList()
				: attatchments.stream()
						.map(Attatchment::toFileDto)
						.toList();

		dto.setAttatchments(fileDtos);

		List<TimelineDto> timelineDtos = (timelineEvents == null)
				? Collections.emptyList()
				: timelineEvents.stream()
						.map(Timeline::toDto)
						.toList();

		dto.setTimelines(timelineDtos);

		return dto;
	}

}
