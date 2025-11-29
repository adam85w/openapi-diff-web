package net.adam85w.openapi.openapidiff.web;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
class OpenApiMapper {

    private final YAMLMapper yamlMapper;

    private final JsonMapper jsonMapper;

    OpenApiMapper(YAMLMapper yamlMapper, JsonMapper jsonMapper) {
        this.yamlMapper = yamlMapper;
        this.jsonMapper = jsonMapper;
    }

    OpenAPI map(InputStream inputStream, OpenApiFormat format) throws InvalidOpenAPIException, IOException {
        if (OpenApiFormat.YAML == format) {
            return yamlMapper.readValue(inputStream, OpenAPI.class);
        }
        return jsonMapper.readValue(inputStream, OpenAPI.class);
    }
}
