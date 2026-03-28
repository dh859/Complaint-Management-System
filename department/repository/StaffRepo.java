package com.cms.cmsapp.department.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.department.entity.ExternalDepartment;
import com.cms.cmsapp.department.entity.Staff;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.common.Enums.Category;
import com.cms.cmsapp.common.Enums.DepartmentRole;
import com.cms.cmsapp.common.Enums.Status;

@Repository
public interface StaffRepo extends JpaRepository<Staff, Long> {

    Optional<List<Staff>> findByIsActiveTrue();

    Optional<List<Staff>> findByDepartment(ExternalDepartment department);

    Optional<List<Staff>> findByDepartmentRole(DepartmentRole departmentRole);

    boolean existsByUserAndDepartment(User user, ExternalDepartment department);

    Optional<List<Staff>> findByIsActiveTrueAndSkillsContaining(Category skill);

    Optional<List<Staff>> findByIsActiveTrueAndDepartmentAndSkillsContaining(ExternalDepartment department,
            Category skill);

    @Query("""
                SELECT c.assignedToUser.id, COUNT(c)
                FROM Complaint c
                WHERE c.status IN ('OPEN','IN_PROGRESS')
                GROUP BY c.assignedToUser.id
            """)
    List<Object[]> getActiveCaseCounts();

    @Query("""
                SELECT s
                FROM Staff s
                WHERE s.isActive = true
                AND (
                    SELECT COUNT(c)
                    FROM Complaint c
                    WHERE c.assignedToUser = s.user
                    AND c.status IN :activeStatuses
                ) < :maxCases
            """)
    List<Staff> findEligibleStaffByLoad(
            @Param("maxCases") int maxCases,
            @Param("activeStatuses") List<Status> activeStatuses);

    @Query("""
                SELECT DISTINCT s
                FROM Staff s
                LEFT JOIN s.skills skill
                WHERE s.isActive = true
                AND (
                    SELECT COUNT(c)
                    FROM Complaint c
                    WHERE c.assignedToUser = s.user
                    AND c.status IN :activeStatuses
                ) < :maxCases
                AND (:matchSkill = false OR skill = :category)
            """)
    List<Staff> findEligibleStaff(
            @Param("maxCases") int maxCases,
            @Param("activeStatuses") List<Status> activeStatuses,
            @Param("category") Category category,
            @Param("matchSkill") boolean matchSkill);

    @Query("""
                SELECT s
                FROM Staff s
                LEFT JOIN Complaint c
                    ON c.assignedToUser = s.user
                    AND c.status IN :activeStatuses
                WHERE s.isActive = true
                GROUP BY s
                HAVING COUNT(c) < :maxCases
            """)
    List<Staff> findEligibleStaffOptimized(
            @Param("maxCases") int maxCases,
            @Param("activeStatuses") List<Status> activeStatuses);

    @Query("""
                SELECT s
                FROM Staff s
                LEFT JOIN Complaint c
                    ON c.assignedToUser = s.user
                    AND c.status IN :activeStatuses
                WHERE s.isActive = true
                AND (:matchSkill = false OR :category MEMBER OF s.skills)
                GROUP BY s
                HAVING COUNT(c) < :maxCases
                ORDER BY COUNT(c) ASC
            """)
    List<Staff> findEligibleStaffOrdered(
            @Param("maxCases") int maxCases,
            @Param("activeStatuses") List<Status> activeStatuses,
            @Param("category") Category category,
            @Param("matchSkill") boolean matchSkill);

}