package com.cms.cmsapp.complaint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.complaint.entity.EscalationHistory;

@Repository
public interface EscalationRepo extends JpaRepository<EscalationHistory, Long> {
    
}
