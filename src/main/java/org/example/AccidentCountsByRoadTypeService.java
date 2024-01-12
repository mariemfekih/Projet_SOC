package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
@RequestMapping("/accidentCountsByRoadType")
public class AccidentCountsByRoadTypeService {

    private static final String JSON_FILE_PATH = "accidentsByRoadType.json";

    @GetMapping("/getRoadTypes")
    public List<String> getAccidentRoadTypes() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Map<String, Integer>> accidentsByRoadType = objectMapper.readValue(jsonFile, Map.class);

            // Assuming the road types are stored in the "accidentsByRoadType" key
            return new ArrayList<>(accidentsByRoadType.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return new ArrayList<>();
        }
    }

    @GetMapping("/getRoadTypes/{roadName}")
    public Map<String, Integer> getAccidentCountsByRoadType(@PathVariable String roadName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Map<String, Integer>> accidentsByRoadType = objectMapper.readValue(jsonFile, Map.class);

            // Assuming the road types are stored in the "accidentsByRoadType" key
            return accidentsByRoadType.get(roadName);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AccidentCountsByRoadTypeService.class, args);
    }
}
