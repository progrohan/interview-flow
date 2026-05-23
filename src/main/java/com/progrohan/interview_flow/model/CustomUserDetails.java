package com.progrohan.interview_flow.model;

import com.progrohan.interview_flow.entity.Role;
import com.progrohan.interview_flow.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final Long id;
    private final String username;
    private final String password;
    @Getter
    private final Role role;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
