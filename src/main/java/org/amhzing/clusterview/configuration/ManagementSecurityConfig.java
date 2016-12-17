package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.configuration.properties.ManagementProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableConfigurationProperties(ManagementProperties.class)
@Order(ManagementServerProperties.ACCESS_OVERRIDE_ORDER)
public class ManagementSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ManagementProperties managementProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .requestMatchers(request -> StringUtils.startsWith(request.getRequestURI(), managementProperties.getContextPath()))
                .and()
            .authorizeRequests()
                .antMatchers(managementProperties.getContextPath() + "/health").permitAll()
                .anyRequest().hasAuthority("ROLE_SUPER_ADMIN")
                .and()
            .httpBasic();
    }
}
