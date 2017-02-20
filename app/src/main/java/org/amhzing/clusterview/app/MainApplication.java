package org.amhzing.clusterview.app;

import org.amhzing.clusterview.backend.BackendApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.stagemonitor.core.Stagemonitor;

@SpringBootApplication
@Import(BackendApplication.class)
public class MainApplication {

    public static void main(String[] args) {
        Stagemonitor.init();
        SpringApplication.run(MainApplication.class, args);
    }
}
