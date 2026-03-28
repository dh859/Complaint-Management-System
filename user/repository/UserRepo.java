package com.cms.cmsapp.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.user.entity.User;





@Repository
public interface UserRepo extends JpaRepository<User,Long>{
    Optional<User>  findByUsername(String username);
    Optional<List<User>> findByRole(Role role);
    
    long countByRole(Role role);
    long countByRoleAndIsActive(Role role,boolean active);
    long countByIsActive(boolean active);
}
