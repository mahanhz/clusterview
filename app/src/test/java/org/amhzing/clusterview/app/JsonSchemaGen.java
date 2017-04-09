package org.amhzing.clusterview.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjetland.jackson.jsonSchema.JsonSchemaGenerator;
import org.amhzing.clusterview.app.api.ClustersDTO;
import org.amhzing.clusterview.app.api.GroupDTO;
import org.amhzing.clusterview.app.api.GroupsDTO;
import org.amhzing.clusterview.app.api.ReferenceActivitiesDTO;
import org.amhzing.clusterview.app.api.cache.CacheDTO;
import org.amhzing.clusterview.app.api.statistic.ActivitiesDTO;
import org.amhzing.clusterview.app.api.statistic.CoursesDTO;
import org.amhzing.clusterview.app.api.statistic.HistoricalActivitiesDTO;

import java.io.FileWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class JsonSchemaGen {

    private static final String JSON_EXT = ".json";

    public static void main(String[] args) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(objectMapper);

        pojos().entrySet().forEach(entry -> {
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

    private static Map<Class, String> pojos() {
        final Map<Class, String> map = new HashMap<>();

        map.put(HistoricalActivitiesDTO.class, HistoricalActivitiesDTO.class.getSimpleName() + JSON_EXT);
        map.put(ClustersDTO.class, ClustersDTO.class.getSimpleName() + JSON_EXT);
        map.put(ReferenceActivitiesDTO.class, ReferenceActivitiesDTO.class.getSimpleName() + JSON_EXT);
        map.put(CoursesDTO.class, CoursesDTO.class.getSimpleName() + JSON_EXT);
        map.put(ActivitiesDTO.class, ActivitiesDTO.class.getSimpleName() + JSON_EXT);
        map.put(GroupDTO.class, GroupDTO.class.getSimpleName() + JSON_EXT);
        map.put(GroupsDTO.class, GroupsDTO.class.getSimpleName() + JSON_EXT);
        map.put(CacheDTO.class, CacheDTO.class.getSimpleName() + JSON_EXT);

        return map;
    }
}
