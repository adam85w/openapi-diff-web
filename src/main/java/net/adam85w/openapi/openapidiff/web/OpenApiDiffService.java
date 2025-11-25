package net.adam85w.openapi.openapidiff.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class OpenApiDiffService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiDiffService.class);

    private final ObjectMapper mapper;

    OpenApiDiffService(ObjectMapper yamlObjectMapper) {
        mapper = yamlObjectMapper;
    }

    ChangedOpenApi diff(InputStreamSource currentVersion, InputStreamSource newVersion) throws InvalidOpenAPIException {
        OpenAPI currentOpenApi = obtainOpenAPI(currentVersion).orElseThrow(() -> new InvalidOpenAPIException("Provided an invalid current version of the OpenAPI contract."));
        OpenAPI newOpenApi = obtainOpenAPI(newVersion).orElseThrow(() -> new InvalidOpenAPIException("Provided an invalid new version of the OpenAPI contract."));
        return OpenApiCompare.fromSpecifications(currentOpenApi, newOpenApi);
    }

    protected Optional<OpenAPI> obtainOpenAPI(InputStreamSource inputStreamSource) {
        try {
            return Optional.of(mapper.readValue(inputStreamSource.getInputStream(), OpenAPI.class));
        } catch (Exception e) {
            LOGGER.warn("Error while reading OpenAPI from input stream", e);
            return Optional.empty();
        }
    }
}
