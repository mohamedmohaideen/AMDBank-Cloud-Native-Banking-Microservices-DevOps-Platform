package com.deena.accounts;

import com.deena.accounts.Dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@ConfigurationPropertiesScan
@EnableFeignClients
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "AMDBank Accounts microservices REST API Documentation",
				version = "V1",
				contact = @Contact(
						name = "Mohamed Mohaideen",
						email = "deensmsd07@gmail.com",
						url = "https://mohamedmohaideen.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.mohamedmohaideen.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "AMDBank Accounts microservice REST API Documentation",
				url = "https://www.mohamedmohaideen.com/swagger-ui.html"
		)
		)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
