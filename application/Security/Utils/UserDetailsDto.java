package com.cms.cmsapp.application.Security.Utils;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cms.cmsapp.user.entity.User;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto implements UserDetails {

    private Long userId;
    private String username;

    private String password;

    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(authorities.get(0));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getUserId(){
        return userId;
    }

    public String getRole(){
        return authorities.get(0).getAuthority();
    }

    public UserDetailsDto(User user){
        this.userId=user.getUserId();
        this.username=user.getUsername();
        this.password=user.getPassword();
        this.authorities=List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
    }



    
    
}
