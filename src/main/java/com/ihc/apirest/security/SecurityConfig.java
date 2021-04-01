package com.ihc.apirest.security;

import com.ihc.apirest.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
  UserDetailsServiceImpl userDetailsService;



	@Bean
	public BCryptPasswordEncoder passwordEncoder() 
	{
    return new BCryptPasswordEncoder();
  }


  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception 
  {
    return super.authenticationManagerBean();
  }


  @Bean
  public JwtTokenFilter jwtTokenFilter()
  {
    return new JwtTokenFilter();
  }
  

  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception 
  {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception 
  {
      http
          .cors()
          .and()
          .csrf().disable()
          .authorizeRequests()
          .antMatchers("/v1/signup").permitAll()
          .antMatchers("/v1/login").permitAll()
          .antMatchers("/v1/articulos").permitAll()
          .antMatchers("/v1/ciudades").permitAll()
          .antMatchers("/v1/restaurar").permitAll()
          .anyRequest().authenticated()
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
          // .and().formLogin()
          // .and().logout()
      ;
  }
}