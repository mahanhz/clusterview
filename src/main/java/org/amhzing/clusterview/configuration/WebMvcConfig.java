package org.amhzing.clusterview.configuration;

import org.amhzing.clusterview.web.model.ActivityModelFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(final FormatterRegistry formatterRegistry) {
        formatterRegistry.addFormatter(new ActivityModelFormatter());
    }
}
