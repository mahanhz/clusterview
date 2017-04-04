package org.amhzing.clusterview.app.configuration;

import org.amhzing.clusterview.app.annotation.ConditionalOnRestEnabled;
import org.amhzing.clusterview.app.configuration.handler.UserHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ConditionalOnRestEnabled
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new UserHandlerInterceptor());

        super.addInterceptors(registry);
    }
}
