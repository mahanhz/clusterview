package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.web.model.ActivityModelFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final CacheControl cacheControl = CacheControl.empty();
        cacheControl.sMaxAge(2, TimeUnit.HOURS);
        cacheControl.mustRevalidate();

        Stream.of(StaticFiles.values()).forEach(resource -> {
            registry.addResourceHandler(resource.getResourcePattern())
                    .addResourceLocations("classpath:/static" + resource.getResourceLocation())
                    .setCacheControl(cacheControl);
        });

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .setCacheControl(cacheControl)
                .resourceChain(true)
                .addResolver(new WebJarsResourceResolver());

        super.addResourceHandlers(registry);
    }
}
