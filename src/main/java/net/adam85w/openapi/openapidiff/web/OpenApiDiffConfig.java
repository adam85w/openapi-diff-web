package net.adam85w.openapi.openapidiff.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiDiffConfig {

    @Bean
    ObjectMapper buildYamlObjectMapper() {
        return new ObjectMapper(new YAMLFactory());
    }
}
