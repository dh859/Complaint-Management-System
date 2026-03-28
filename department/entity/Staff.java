package com.cms.cmsapp.department.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.DepartmentRole;
import com.cms.cmsapp.department.dto.StaffDetailsDto;
import com.cms.cmsapp.department.dto.StaffRecordDto;
import com.cms.cmsapp.user.entity.User;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "department_staff",
    indexes = {
        @Index(name = "idx_staff_active", columnList = "is_active"),
        @Index(name = "idx_staff_department", columnList = "department_id"),
        @Index(name = "idx_staff_user", columnList = "user_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"department_id", "user_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private ExternalDepartment department;

    @Enumerated(EnumType.STRING)
    @Column(name = "department_role", nullable = false, length = 20)
    private DepartmentRole departmentRole;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(nullable = false)
    private boolean onLeave = false;

    private LocalDateTime lastActiveAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "staff_skills",
        joinColumns = @JoinColumn(name = "staff_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 30)
    private Set<Category> skills = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public boolean isAvailable() {
        return isActive && !onLeave;
    }

    public StaffRecordDto toStaffRecordDto() {
        return StaffRecordDto.builder()
                .staffId(this.id)
                .departmentName(this.department.getName())
                .fullName(this.user.getFullname())
                .departmentRole(this.departmentRole.name())
                .isActive(this.isActive)
                .build();
    }

    public StaffDetailsDto toStaffDetailsDto() {
        return StaffDetailsDto.builder()
                .staffId(this.id)
                .departmentName(this.department.getName())
                .fullName(this.user.getFullname())
                .departmentRole(this.departmentRole.name())
                .isActive(this.isActive)
                .skills(this.skills)
                .userId(this.user.getUserId())
                .userName(this.user.getUsername())
                .email(this.user.getEmail())
                .contactNumber(this.user.getContactNumber())
                .address(this.user.getAddress())
                .state(this.user.getState())
                .city(this.user.getCity())
                .build();
    }
}
