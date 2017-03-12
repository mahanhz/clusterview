package org.amhzing.clusterview.app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.config.EnableHypermediaSupport;

// For this configuration to work then Rest controllers should not contain produces
// as this config will add application/hal+json
@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class RestConfig {

}
