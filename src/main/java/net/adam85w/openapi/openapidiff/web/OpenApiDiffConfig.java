package net.adam85w.openapi.openapidiff.web;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class OpenApiDiffConfig {

    @Bean
    YAMLMapper buildYamlMapper() {
        return new YAMLMapper();
    }

    @Bean
    @Primary
    JsonMapper buildJsonMapper() {
        return new JsonMapper();
    }
}
