package net.adam85w.openapi.openapidiff.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
class OpenApiDiffService {

    private final ObjectMapper mapper;

    OpenApiDiffService(ObjectMapper yamlObjectMapper) {
        mapper = yamlObjectMapper;
    }

    ChangedOpenApi diff(InputStream currentVersion, InputStream newVersion) throws IOException {
         var currentOpenApi = mapper.readValue(currentVersion, OpenAPI.class);
         var newOpenApi = mapper.readValue(newVersion, OpenAPI.class);
         return OpenApiCompare.fromSpecifications(currentOpenApi, newOpenApi);
    }
}
