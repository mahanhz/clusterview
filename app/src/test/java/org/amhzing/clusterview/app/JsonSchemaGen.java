package org.amhzing.clusterview.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.amhzing.clusterview.app.api.ClustersDTO;
import org.amhzing.clusterview.app.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.app.api.statistic.ActivitiesDTO;
import org.amhzing.clusterview.app.api.statistic.CoursesDTO;
import org.amhzing.clusterview.app.api.statistic.HistoricalActivitiesDTO;

import java.io.FileWriter;
import java.net.URL;
import java.util.Map;

public final class JsonSchemaGen {

    private static final String JSON_EXT = ".json";
    private static Map<Class, String> POJOS = ImmutableMap.of(HistoricalActivitiesDTO.class, HistoricalActivitiesDTO.class.getSimpleName() + JSON_EXT,
                                                              ClustersDTO.class, ClustersDTO.class.getSimpleName() + JSON_EXT,
                                                              ReferenceActivitiesDTO.class, ReferenceActivitiesDTO.class.getSimpleName() + JSON_EXT,
                                                              CoursesDTO.class, CoursesDTO.class.getSimpleName() + JSON_EXT,
                                                              ActivitiesDTO.class, ActivitiesDTO.class.getSimpleName() + JSON_EXT);

    public static void main(String[] args) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        POJOS.entrySet().forEach(entry -> {
            final JsonNode jsonSchema = jsonSchemaGenerator.generateJsonSchema(entry.getKey());
            String jsonSchemaAsString = "";

            final URL resource = JsonSchemaGen.class.getClassLoader().getResource("json-schema/" + entry.getValue());

            try {
                jsonSchemaAsString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema);

                try (FileWriter file = new FileWriter(resource.getFile())) {
                    file.write(jsonSchemaAsString);

                    System.out.println("Successfully wrote json schema to " + resource.getFile() + ". This should be copied over to src/test/resources/json-schema.");
                }
            } catch (Exception ex) {
                System.out.println("Unfortuantely and exception occurred: " + ex);
            }
        });
    }
}
