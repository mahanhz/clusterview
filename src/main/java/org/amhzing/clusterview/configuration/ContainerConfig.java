package org.amhzing.clusterview.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.stagemonitor.web.WebPlugin;

import javax.servlet.ServletContext;

@Configuration
public class ContainerConfig implements EmbeddedServletContainerCustomizer {

    @Override
    public void customize(final ConfigurableEmbeddedServletContainer container) {
        container.addInitializers(this::stagemonitorEnabler);
    }

    private void stagemonitorEnabler(final ServletContext servletContext) {
        new WebPlugin().onStartup(null, servletContext);
    }
}
