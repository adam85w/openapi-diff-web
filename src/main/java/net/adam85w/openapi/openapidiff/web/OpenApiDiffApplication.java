package net.adam85w.openapi.openapidiff.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OpenAPI Diff WEB interface
 * Web interface for the OpenAPI Diff project: https://github.com/OpenAPITools/openapi-diff
 *
 * @author Adam Woźniak <adam85.w@gmail.com>
 */
@SpringBootApplication
public class OpenApiDiffApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenApiDiffApplication.class, args);
	}
}
