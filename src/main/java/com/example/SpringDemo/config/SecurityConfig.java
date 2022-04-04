package com.example.SpringDemo.config;

import com.example.SpringDemo.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    //преопределяеться метод с HttpSecurity http
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //аутинфикация пользователя(к каким ресурсам
                // пользователь имеет доступ)
                .authorizeRequests()
                //antMatchers() указывает к каким конкретно ресурсам
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET,"/show/**").hasAnyRole(Role.ADMIN.name(),Role.USER.name())
                .antMatchers(HttpMethod.POST,"/action/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/action/**").hasRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                // user должен быть SpringSecurity !!!
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles(Role.ADMIN.name())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .roles(Role.USER.name())
                        .build()
        );
    }
        @Bean
        protected PasswordEncoder passwordEncoder(){
            return  new BCryptPasswordEncoder(12);
        }

}
