package com.p2c.p2c.security;

import com.p2c.p2c.model.Parent;
import com.p2c.p2c.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails
{

    private Parent parent;

    public UserPrincipal(Parent parent)
    {
        this.parent = parent;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Role role = parent.getRole();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getRole());
        authorities.add(authority);
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return this.parent.getPassword();
    }

    @Override
    public String getUsername()
    {
        return this.parent.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
