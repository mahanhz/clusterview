package org.amhzing.clusterview.app.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;
import static org.assertj.core.api.Assertions.assertThat;

public class SchemaValidationHelper {

    private SchemaValidationHelper() {
    }

    public static void assertSuccessfulSchemaValidation(final ResultActions result,
                                                        final String resource) throws IOException, ProcessingException {
        notNull(result);
        notBlank(resource);

        final String jsonContent = result.andReturn().getResponse().getContentAsString();

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode actualJson = mapper.readTree(jsonContent);

        final JsonNode schemaFile = JsonLoader.fromResource("/json-schema/" + resource);
        final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        final JsonSchema schema = factory.getJsonSchema(schemaFile);

        final ProcessingReport report = schema.validate(actualJson);

        assertThat(report.isSuccess()).isTrue();
    }
}
