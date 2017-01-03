package org.amhzing.clusterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.stagemonitor.core.Stagemonitor;

@SpringBootApplication
public class ClusterviewApplication {

	public static void main(String[] args) {
		Stagemonitor.init();
		SpringApplication.run(ClusterviewApplication.class, args);
	}
}
