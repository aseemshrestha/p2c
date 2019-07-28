package com.p2c.p2c.security;

import com.p2c.p2c.model.Parent;
import com.p2c.p2c.repository.ParentRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalDetailService implements UserDetailsService
{

    private final ParentRepository parentRepository;

    public UserPrincipalDetailService(ParentRepository parentRepository)
    {
        this.parentRepository = parentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
    {
        Optional<Parent> parent = this.parentRepository.findByUsername(s);
        UserPrincipal userPrincipal = new UserPrincipal(parent.get());
        return userPrincipal;
    }
}
