package com.kokabmedia.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.kokabmedia.service.UserServiceImpl.UserSecurityService;

/*
 * This class handles customised security configurations for both WebSecurity 
 * and HttpSecurit
 * 
 * @Configuration annotation spring indicates that this class has @Bean definition methods,
 * Spring container can process the class and generate Spring Beans to be used in the application.
 */
@Configuration // Lest Spring know this is a configuration class 
@EnableWebSecurity // Enables Spring Security 
@EnableGlobalMethodSecurity(prePostEnabled=true)
/* 
 * The WebSecurityConfigurerAdapter allows Spring to make configuration
 * on web security components.
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * The @Autowired annotation tells the Spring framework that the Environment bean 
	 * and its implementation is an dependency of SecurityConfig class. It is a mechanism 
	 * for implementing Spring dependency injection.
	 * 
	 * @Autowired annotation enables dependency injection with Spring framework to avoid 
	 * tight coupling and enable loose coupling by calling a interface or the implementation 
	 * of an interface.
	 * 
	 * The Spring framework creates a instance (bean) of the Environment or its implementation 
	 * and inject (autowires) that instance into the SecurityConfig object when it is instantiated 
	 * as a autowired dependency.
	 * 
	 * The Environment and its implementation is now a dependency of the SecurityConfig class.
	 */
    @Autowired
    private Environment env;

    @Autowired
    private UserSecurityService userSecurityService; // Customise security service

    // SALT used to encrypt the password in the database.
    private static final String SALT = "salt"; // Salt should be protected carefully

    // Method to encrypt password with salt string 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
        
    }

    // List of specific paths that we would like access publicly without Spring security detection
    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**/*",
            "/console/**",
            "/signup"            
    };

    // Configure method to authorise requests 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().
//                antMatchers("/**").
                antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();

        http
                .csrf().disable().cors().disable()
                .formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe();
    }


    // Authentication Manager for the user security service that has been developed 
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
   	// auth.inMemoryAuthentication().withUser("user").password("password").roles("USER"); //This is in-memory authentication
     auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
    }


}
