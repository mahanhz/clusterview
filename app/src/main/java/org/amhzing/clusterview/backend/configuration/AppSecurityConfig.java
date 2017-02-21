package org.amhzing.clusterview.backend.configuration;

import org.amhzing.clusterview.backend.configuration.handler.RedirectAuthenticationSuccessHandler;
import org.amhzing.clusterview.backend.user.WebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.amhzing.clusterview.backend.configuration.StaticFiles.*;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/stagemonitor/**").permitAll()
            .antMatchers("/backend/**").permitAll()
                .antMatchers("/clusterview/{country}/**").access("hasRole('USER') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/statsview/history/{country}/**").access("hasRole('USER') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/clusteredit/{country}/**").access("hasRole('ADMIN') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/statsedit/history/{country}/**").access("hasRole('ADMIN') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/swagger-ui.html**").hasRole("SUPER_ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(new RedirectAuthenticationSuccessHandler())
                .permitAll() // Login page is accessible to anybody
                .and()
            .logout()
                .logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .permitAll() // Logout success page is accessible to anybody
                .and()
            .headers()
                .frameOptions()
                .sameOrigin(); // To allow pages from the same domain inside an iframe (e.g. inside a lightbox)
    }

    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        web.ignoring().antMatchers(CSS.getResourcePattern(),
                                   JS.getResourcePattern(),
                                   IMAGES.getResourcePattern(),
                                   CSS_SE.getResourcePattern(),
                                   JS_SE.getResourcePattern(),
                                   IMAGES_SE.getResourcePattern(),
                                   "/webjars/**");
    }

    @Bean
    public WebSecurity webSecurity() {
        return new WebSecurity();
    }
}
