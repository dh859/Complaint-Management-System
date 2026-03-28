package com.cms.cmsapp.reporting.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.common.Enums.ReportType;
import com.cms.cmsapp.reporting.entity.Report;
import com.cms.cmsapp.user.entity.User;

@Repository
public interface ReportRepo extends JpaRepository<Report,Long>{

    public Optional<List<Report>> findByReportType(ReportType reportType);
    public Optional<List<Report>>  findByGeneratedByUser(User user);

    public Optional<Report> findByReportId(Long reportId);

    @Query(value = "SELECT report_id, report_type, generated_by_user_id FROM Report", nativeQuery = true)
    public Optional<List<Report>> getAll();


    
}
