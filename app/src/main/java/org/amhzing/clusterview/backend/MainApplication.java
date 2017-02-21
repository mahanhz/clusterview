package org.amhzing.clusterview.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stagemonitor.core.Stagemonitor;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		Stagemonitor.init();
		SpringApplication.run(MainApplication.class, args);
	}
}
