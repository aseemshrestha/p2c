package com.p2c.p2c.controllers;

import com.p2c.p2c.constants.RoleBuilder;
import com.p2c.p2c.model.LoginParam;
import com.p2c.p2c.model.Parent;
import com.p2c.p2c.repository.ParentRepository;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping( "/api/public" )
public class PublicController
{
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    public PublicController(ParentRepository parentRepository,
        PasswordEncoder passwordEncoder)
    {
        this.parentRepository = parentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping( "addNew/parent" )
    public ResponseEntity<Parent> registerParent(@RequestBody Parent parent, HttpServletRequest request)
    {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        String ipAdd = request.getHeader("X-FORWARDED-FOR");
        parent.setUsername(parent.getEmail());
        parent.setRole(RoleBuilder.registeredUser);
        parent.setPassword(passwordEncoder.encode(parent.getPassword()));
        parent.setCreated(new Date());
        parent.setIsActive(1);
        parent.setLastUpdated(new Date());
        parent.setBrowser(userAgent.getBrowser() + "-" + userAgent.getOperatingSystem());
        parent.setIp(ipAdd == null ? request.getRemoteAddr() : ipAdd);
        Parent parentNew = this.parentRepository.save(parent);
        return new ResponseEntity<>(parentNew, HttpStatus.CREATED);
    }
}

