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
@RequestMapping("/accidentCountsByLocation")
public class AccidentCountsByLocationService {
    private static final String JSON_FILE_PATH = "accidentsByLocation.json";

    @GetMapping("/getLocations")
    public List<String> getAccidentLocations() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, Map.class);

            // Assuming the locations are stored in the "accidentsByLocation" key
            Map<String, Integer> accidentsByLocation = (Map<String, Integer>) jsonResult.get("accidentsByLocation");
            return new ArrayList<>(accidentsByLocation.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return new ArrayList<>();
        }
    }

    @GetMapping("/getLocations/{locationName}")
    public Integer getAccidentCountsByLocation(@PathVariable String locationName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, Map.class);

            // Assuming the locations are stored in the "accidentsByLocation" key
            Map<String, Integer> accidentsByLocation = (Map<String, Integer>) jsonResult.get("accidentsByLocation");
            return accidentsByLocation.get(locationName);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AccidentCountsByLocationService.class, args);
    }
}
