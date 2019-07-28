package com.p2c.p2c.security;

import com.p2c.p2c.constants.Role;
import com.p2c.p2c.repository.ParentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static java.util.Arrays.asList;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    private final UserPrincipalDetailService userPrincipalDetailService;
    private final ParentRepository parentRepository;

    private static final String ROLE_ADMIN = Role.ADMIN.name();
    private static final String ROLE_REGISTERED_USER = Role.REGISTERED_USER.name();

    public SecurityConfig(UserPrincipalDetailService userPrincipalDetailService,
        ParentRepository parentRepository)
    {
        this.userPrincipalDetailService = userPrincipalDetailService;
        this.parentRepository = parentRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    {
        auth.authenticationProvider(authenticationProvider());

    }

    @Override
    public void configure(HttpSecurity http) throws Exception
    {

        http
            .csrf()
            .disable()
            .cors()
            .and()
            //  .addFilterBefore(corsFilter(), SessionManagementFilter.class)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager()))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), this.parentRepository))
            .authorizeRequests()
            // configure access role
            .antMatchers("/login").permitAll()
            .antMatchers("/api/public/**").permitAll()
            .antMatchers("/api/user/**").hasAnyRole(ROLE_REGISTERED_USER, ROLE_ADMIN)
            .antMatchers("/api/post/**").hasAnyRole(ROLE_REGISTERED_USER, ROLE_ADMIN)
            .antMatchers("/api/admin/**").hasRole(ROLE_ADMIN)
            .and()
            .httpBasic();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userPrincipalDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(asList("*"));
        configuration.setAllowedHeaders(asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                                               "Access-Control-Request-Method", "Access-Control-Request-Headers",
                                               "Origin", "Cache-Control", "Content-Type", "Authorization"));
        configuration.setExposedHeaders(asList(/*"Access-Control-Allow-Headers",*/
            "Authorization"/*, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With",
                                               "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"*/));

        configuration.setAllowedMethods(asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

