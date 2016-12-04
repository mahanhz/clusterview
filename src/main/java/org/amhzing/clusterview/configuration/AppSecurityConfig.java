package org.amhzing.clusterview.configuration;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/clusterview/se/**").hasRole("SE_USER")
                .antMatchers("/clusteredit/se/**").hasRole("SE_ADMIN")
                .antMatchers("/manage/**").hasRole("SUPER_ADMIN")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                //.successHandler(new RedirectAuthenticationSuccessHandler())
                .permitAll() // Login page is accessible to anybody
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll() // Logout success page is accessible to anybody
                .and()
            .sessionManagement()
                .sessionFixation()
                .newSession(); // Create completely new session
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }
}
