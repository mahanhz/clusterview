package org.amhzing.clusterview.appui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.stagemonitor.core.Stagemonitor;

@SpringBootApplication
@Import(org.amhzing.clusterview.app.MainApplication.class)
public class MainUIApplication {

	public static void main(String[] args) {
		Stagemonitor.init();
		SpringApplication.run(MainUIApplication.class, args);
	}
}
