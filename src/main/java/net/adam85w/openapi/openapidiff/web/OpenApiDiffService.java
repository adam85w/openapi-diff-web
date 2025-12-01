package net.adam85w.openapi.openapidiff.web;

import io.swagger.v3.oas.models.OpenAPI;
import org.openapitools.openapidiff.core.OpenApiCompare;
import org.openapitools.openapidiff.core.model.ChangedOpenApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
class OpenApiDiffService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenApiDiffService.class);

    private final OpenApiMapper mapper;

    OpenApiDiffService(OpenApiMapper openApiMapper) {
        mapper = openApiMapper;
    }

    ChangedOpenApi diff(InputStreamSource currentVersion, InputStreamSource newVersion, OpenApiFormat format) throws InvalidOpenAPIException {
        OpenAPI currentOpenApi = obtainOpenAPI(currentVersion, format).orElseThrow(() -> new InvalidOpenAPIException("Provided an invalid current version of the OpenAPI contract."));
        OpenAPI newOpenApi = obtainOpenAPI(newVersion, format).orElseThrow(() -> new InvalidOpenAPIException("Provided an invalid new version of the OpenAPI contract."));
        ChangedOpenApi result = OpenApiCompare.fromSpecifications(currentOpenApi, newOpenApi);
        validAgainstDetachedModel(result, currentVersion, newVersion);
        return result;
    }

    protected Optional<OpenAPI> obtainOpenAPI(InputStreamSource inputStreamSource, OpenApiFormat format) throws InvalidOpenAPIException {
        try {
            return Optional.of(mapper.map(inputStreamSource.getInputStream(), format));
        } catch (Exception e) {
            LOGGER.warn("Error while reading OpenAPI from input stream", e);
            return Optional.empty();
        }
    }

    protected void validAgainstDetachedModel(ChangedOpenApi result, InputStreamSource currentVersion, InputStreamSource newVersion) throws InvalidOpenAPIException {
        try {
            if (!result.isDifferent() && currentVersion.getInputStream().readAllBytes().length != newVersion.getInputStream().readAllBytes().length) {
                throw new InvalidOpenAPIException("The versions match, but there is a size issue - probably one of the objects is detached from the model or something doesn’t comply with the OpenAPI specification.");
            }
        } catch (IOException e) {
            LOGGER.warn("Error while reading OpenAPI from input stream", e);
            throw new InvalidOpenAPIException("The versions match, but there is a size issue - probably one of the objects is detached from the model or something doesn’t comply with the OpenAPI specification.");
        }
    }
}
