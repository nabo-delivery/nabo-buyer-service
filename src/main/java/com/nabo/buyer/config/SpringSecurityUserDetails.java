package com.nabo.buyer.config;


import com.nabo.buyer.repository.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


public class SpringSecurityUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    public SpringSecurityUserDetails() {
    }

    public SpringSecurityUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {

        return user.getEmail();
    }

    //TODO ADD OVERRIDE COLUMNS TO THE DB
    @Override
    public boolean isAccountNonExpired() {

//		return user.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

//		return user.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

//		return user.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {

//		return user.isEnabled();
        return true;
    }

}

