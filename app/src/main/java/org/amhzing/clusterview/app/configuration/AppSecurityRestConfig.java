package org.amhzing.clusterview.app.configuration;

import org.amhzing.clusterview.app.annotation.ConditionalOnRestEnabled;
import org.amhzing.clusterview.app.user.WebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@ConditionalOnRestEnabled
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppSecurityRestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**/clusterview/{country}/**").access("hasRole('USER') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/**/statsview/history/{country}/**").access("hasRole('USER') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/**/clusteredit/{country}/**").access("hasRole('ADMIN') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/**/statsedit/history/{country}/**").access("hasRole('ADMIN') and @webSecurity.checkCountry(authentication, #country)")
                .antMatchers("/swagger-ui.html**").hasRole("SUPER_ADMIN")
                .antMatchers("/cloudfoundryapplication/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .httpBasic();
    }

    @Bean
    public WebSecurity webSecurity() {
        return new WebSecurity();
    }
}
