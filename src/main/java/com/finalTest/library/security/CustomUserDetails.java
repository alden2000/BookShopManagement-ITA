package com.finalTest.library.security;

import com.finalTest.library.privileges.entity.Privilege;
import com.finalTest.library.privileges.entity.PrivilegeRepository;
import com.finalTest.library.privileges.service.PrivilegeService;
import com.finalTest.library.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.*;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> privileges=new ArrayList<>();
        privileges.add(user.getPrivilege().getName());
        privileges.add(user.getPrivilege().getName());
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (String role : privileges) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

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
