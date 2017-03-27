package org.amhzing.clusterview.app.configuration;

import org.amhzing.clusterview.app.configuration.handler.UserHandlerInterceptor;
import org.amhzing.clusterview.app.web.model.ActivityModelFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(final FormatterRegistry formatterRegistry) {
        formatterRegistry.addFormatter(new ActivityModelFormatter());
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new UserHandlerInterceptor());

        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final CacheControl cacheControl = CacheControl.empty();
        cacheControl.sMaxAge(2, TimeUnit.HOURS);
        cacheControl.mustRevalidate();

        staticFiles(registry, cacheControl);
        webjars(registry, cacheControl);

        super.addResourceHandlers(registry);
    }

    private void webjars(final ResourceHandlerRegistry registry, final CacheControl cacheControl) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCacheControl(cacheControl)
                .resourceChain(true)
                .addResolver(new WebJarsResourceResolver());
    }

    private void staticFiles(final ResourceHandlerRegistry registry, final CacheControl cacheControl) {
        Stream.of(StaticFiles.values()).forEach(resource -> {
            registry.addResourceHandler(resource.getResourcePattern())
                    .addResourceLocations("classpath:/static" + resource.getResourceLocation())
                    .setCacheControl(cacheControl);
        });
    }
}
