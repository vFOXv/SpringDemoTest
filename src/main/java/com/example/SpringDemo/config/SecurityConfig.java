package com.example.SpringDemo.config;

import com.example.SpringDemo.model.Role;
import com.example.SpringDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    @Override
    //преопределяеться метод с HttpSecurity http
    protected void configure(HttpSecurity http) throws Exception {
        http
                //.csrf().disable()
                //аутинфикация пользователя(к каким ресурсам
                // пользователь имеет доступ)
                .authorizeRequests()
                //antMatchers() указывает к каким конкретно ресурсам(всех пускают в корень и страница регистрации)
                .antMatchers("/","/security/registration").permitAll()
                //в "/profile" только аутинфицированных
                //.antMatchers("/profile/**").authenticated()
                //пропуск по ролям - разрешен доступ ADMIN and SUPERADMIN
                //.antMatchers("/admin/**").hasAnyRole("ADMIN","SUPERADMIN")
                .antMatchers(HttpMethod.GET, "/show/**").hasAnyRole("ADMIN", "USER")
                .antMatchers(HttpMethod.POST, "/action/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/action/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                //маленькое окошко для аутификации
                //.httpBasic();
                //страница логина и пароля
                .formLogin()
                .and()
                //после логаута пользователь попадает на корневую страницу(а не на страницу логина и пароля)
                .logout().logoutSuccessUrl("/");
    }

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                // user должен быть SpringSecurity !!!
//                User.builder()
//                        .username("admin")
//                        .password(passwordEncoder().encode("admin"))
//                        .roles(Role.ADMIN.name())
//                        .build(),
//                User.builder()
//                        .username("user")
//                        .password(passwordEncoder().encode("user"))
//                        .roles(Role.USER.name())
//                        .build()
//        );
//    }

//        @Bean
//    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
////    создание пользователей
//        UserDetails user = User.builder()
//                .username("admin")
//                //https://bcrypt-generator.com/  admin
//                .password("{bcrypt}$2a$12$7EjCbvCYnvs1Pmq8Joz2SOb69Jol8IrY94f22Tbwtpxw3EDgGcl9S")
//                .roles(Role.ADMIN.name(),Role.USER.name())
//                .build();
//        UserDetails admin = User.builder()
//                .username("user")
//                //https://bcrypt-generator.com/  user
//                .password("{bcrypt}$2a$12$8X.0ZxcMJor2nCVRv/6TPe3W.yJWn6ehNuXQOkC/utYaUxk26yM2u")
//                .roles(Role.USER.name())
//                .build();
//        //создание списка пользователей     dataSours это настройки DB в resources/application.properties
//        //если пользователи заранее внесены в DB нужна только эта строка
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//        //проверка в DB наличия конкретного пользователя и если он есть - удаление
//        //т.к. если записать пользователя с тем же логином и паролем будет ошибка
//        if(jdbcUserDetailsManager.userExists(user.getUsername())){
//            jdbcUserDetailsManager.deleteUser(user.getUsername());
//        }
//        if(jdbcUserDetailsManager.userExists(admin.getUsername())){
//            jdbcUserDetailsManager.deleteUser(admin.getUsername());
//        }
//        jdbcUserDetailsManager.createUser(user);
//        jdbcUserDetailsManager.createUser(admin);
//        return jdbcUserDetailsManager;
//    }


    //преобразователь паролей(он шифрует их {bcrypt})
    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    //DaoAuthenticationProvider получает логин и пароль и проверяет в DB наличее такого пользователя
    //если пользователь есть, то ложит его в SpringSecurityContext
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //шифрует пароль при помощи PasswordEncoder созданного выше
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        //проверяет наличее пользователя в DB по логину и паролю
        //setUserDetailsService() прописываеться в Services
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    //этот фильтр позволяет работать с таймлифовскими методами DELETE,
    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
}
