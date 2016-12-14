package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.configuration.handler.RedirectAuthenticationSuccessHandler;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.amhzing.clusterview.configuration.StaticFiles.*;
import static org.amhzing.clusterview.user.UserRole.SE_ADMIN;
import static org.amhzing.clusterview.user.UserRole.SE_USER;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/clusterview/se/**").hasAuthority(SE_USER.getRole())
                .antMatchers("/statsview/history/se/**").hasAuthority(SE_USER.getRole())
                .antMatchers("/clusteredit/se/**").hasAuthority(SE_ADMIN.getRole())
                .antMatchers("/statsedit/history/se/**").hasAuthority(SE_ADMIN.getRole())
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(new RedirectAuthenticationSuccessHandler())
                .permitAll() // Login page is accessible to anybody
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll() // Logout success page is accessible to anybody
                .and()
            .sessionManagement()
                .sessionFixation()
                .newSession() // Create completely new session
                .and()
            .headers()
                .frameOptions()
                .sameOrigin(); // To allow pages from the same domain inside an iframe (e.g. inside a lightbox)
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(CSS.getResourcePattern(),
                                   CSS_SE.getResourcePattern(),
                                   JS.getResourcePattern(),
                                   JS_SE.getResourcePattern(),
                                   IMAGES_SE.getResourcePattern(),
                                   "/webjars/**");
    }
}
