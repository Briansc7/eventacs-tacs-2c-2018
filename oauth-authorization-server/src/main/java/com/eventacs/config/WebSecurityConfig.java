package com.eventacs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DataSource datasource;
    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.jdbcAuthentication().dataSource(datasource)
      .withUser("User1").password(passwordEncoder.encode("Pw1")).roles("USER").and()
	  .withUser("usuario").password(passwordEncoder.encode("clave")).roles("USER").and()
	  .withUser("admin1").password(passwordEncoder.encode("111")).roles("ADMIN").and()
	  .withUser("usuario2").password(passwordEncoder.encode("clave")).roles("USER").and()
	  .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
    }// @formatter:on

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
		http.requestMatchers()
//                .antMatchers("/eventacs")
        .and().authorizeRequests().antMatchers("/login").permitAll()
		.antMatchers("/oauth/token/revokeById/**").permitAll()
		.antMatchers("/tokens/**").permitAll()
		.antMatchers("/eventacs/**").permitAll()
                .antMatchers(HttpMethod.POST, "/eventacs/**")
                .access("#oauth2.hasScope('write')")
                .anyRequest().access("#oauth2.hasScope('read')")
        .anyRequest().authenticated()
		//.and().formLogin().permitAll()
		.and().csrf().disable();
        //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		// @formatter:on
    }
}