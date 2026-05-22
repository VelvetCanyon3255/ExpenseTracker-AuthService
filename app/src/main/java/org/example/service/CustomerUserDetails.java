package org.example.service;

import org.apache.catalina.User;
import org.example.entities.UserInfo;
import org.example.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerUserDetails extends UserInfo implements
        UserDetails {

    private String username;
    private String password;




    Collection<? extends GrantedAuthority> authorities;

    public CustomerUserDetails(UserInfo userInfo) {
        this.username = userInfo.getUsername();
        this.password = userInfo.getPassword();

        List<GrantedAuthority> auths = new ArrayList<>();
        for(UserRole role: userInfo.getRoles()){
            auths.add(new SimpleGrantedAuthority(userInfo.getUsername().toUpperCase()));
        }

        this.authorities = auths;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;
    };


    @Override
    public String getUsername(){
        return username;
    };


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
