package com.ssosnik.apprating.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

	@Value("${build.version}")
	private String buildVersion;

	@Bean(name = "com.ssosnik.apprating.configuration.SpringDocConfiguration.apiInfo")
	OpenAPI apiInfo() {
		Contact contact = new Contact();
		contact.setName("Sebastian Sosnik");
		contact.setUrl("https://github.com/ssosnik/app-rating");
		contact.setEmail("s.sosnik@gmail.com");

		return new OpenAPI().info(new Info().title("Apps Rating OpenAPI definition").description(
				"Offers 2 end-points for filtering and sorting apps by rating and age-group")
				.version(buildVersion));
	}
}