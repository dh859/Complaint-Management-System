package com.cms.cmsapp.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cms.cmsapp.application.Security.Utils.UserDetailsDto;
import com.cms.cmsapp.common.Enums.Role;
import com.cms.cmsapp.common.exceptions.ResourceNotFoundException;
import com.cms.cmsapp.user.dto.UserDto;
import com.cms.cmsapp.user.entity.User;
import com.cms.cmsapp.user.repository.UserRepo;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not Found By Username" + username));
    }

    public UserDetails validateUser(String username, String rawPassword) {
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (passwordEncoder.matches(rawPassword, userDetails.getPassword())) {

                return userDetails;
            }
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return new UserDetailsDto(user);
    }

    public List<UserDto> getByType(Role role) {
        return userRepository.findByRole(role).orElseThrow(() -> new ResourceNotFoundException("User Not Found"))
                .stream().map(User::toUserDto).toList();
    }

    public UserDto update(User user){
        return userRepository.save(user).toUserDto();
    }

    public List<UserDto> getAllUsers(){
        return userRepository.findAll().stream().map(User::toUserDto).toList();
    }

    public User getById(Long id){
        return userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found by Id :"+id));
    }

    public void deleteUser(Long id){
        User user=getById(id);
        userRepository.delete(user);
    }

    public long countByRole(Role role){
        return userRepository.countByRole(role);
    }

    public long countByRoleAndIsActive(Role role,boolean active){
        return userRepository.countByRoleAndIsActive(role, active);
    }

    public long countByIsActive(boolean active){
        return userRepository.countByIsActive(active);
    }

}