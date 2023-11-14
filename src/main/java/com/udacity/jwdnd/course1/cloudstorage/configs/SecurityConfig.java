package com.udacity.jwdnd.course1.cloudstorage.configs;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] whiteList = new String[]{"/login", "/signup", "/css/**", "/js/**"};
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(whiteList)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/home", true)
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

}
