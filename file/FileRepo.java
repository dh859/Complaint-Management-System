package com.cms.cmsapp.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.complaint.entity.Attatchment;


@Repository
public interface FileRepo extends JpaRepository<Attatchment,Long>{
    
}
