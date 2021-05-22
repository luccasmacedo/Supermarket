package com.example.Supermarket.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.example.Supermarket.security.SecurityConstants.SIGN_UP_URL;;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/products").hasAnyAuthority("ADMIN","CASHIER")
                .antMatchers(HttpMethod.GET, "/api/v1/products/{id}").hasAnyAuthority("ADMIN", "CASHIER")
                .antMatchers(HttpMethod.POST, "/api/v1/products").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/products/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/products/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/payments").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/payments/{id}").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/payments").hasAnyAuthority("CASHIER")
                .antMatchers(HttpMethod.GET, "/api/v1/reports").hasAnyAuthority("ADMIN")

                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}