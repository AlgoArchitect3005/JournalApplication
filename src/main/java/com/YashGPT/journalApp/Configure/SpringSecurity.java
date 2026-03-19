package com.YashGPT.journalApp.Configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;
import com.YashGPT.journalApp.Service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    // Define the security filter chain to configure HTTP security settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/public/**").permitAll()
                    .requestMatchers("/journal/**","/user/**").authenticated()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
            )
            .httpBasic(withDefaults());

        return http.build();
    }

    // Configure authentication manager to use the custom UserDetailsService and password encoder. Encodes passwords using BCryptPasswordEncoder for secure authentication.
    protected void configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

/*************  ✨ Windsurf Command ⭐  *************/
/**
 * Returns a BCryptPasswordEncoder instance which is used to encode
 * passwords for authentication purposes.
 * @return A BCryptPasswordEncoder instance.
 */
/*******  9dd39f62-3e64-4659-bc35-8273059f73f7  *******/    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
// package com.YashGPT.journalApp.Configure;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.*;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;  
// import com.YashGPT.journalApp.Service.UserDetailsServiceImpl;

// @Configuration
// @EnableWebSecurity
// public class SpringSecurity extends WebSecurityConfigurerAdapter {
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;
//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//         .antMatchers("/journal/**").authenticated()
//         .anyRequest().permitAll()
//         .and()
//         .httpBasic();
//     }
//     @Override
//     protected void configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//     }
//     @Bean
//     public org.springframework.security.crypto.password.PasswordEncoder passwordEncoder() {
//         return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
//     }

// }
