package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
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
@RequestMapping("/accidentReasons")
public class AccidentReasonsService {

    private static final String JSON_FILE_PATH = "accidentsByReason.json";

    @GetMapping("/getReasons")
    public List<String> getAccidentReasons() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

            // Retrieve the nested map under "accidentsByLocation"
            Map<String, Integer> accidentsByLocation = (Map<String, Integer>) jsonResult.get("accidentsByLocation");

            // Extract the keys (accident reasons) from the map
            List<String> accidentReasonsList = new ArrayList<>(accidentsByLocation.keySet());

            return accidentReasonsList;
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return new ArrayList<>();
        }
    }

    @GetMapping("/getReasons/{reason}")
    public Map<String, Integer> getAccidentCountsByReason(@PathVariable String reason) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

            // Retrieve the nested map under "accidentsByLocation"
            Map<String, Integer> accidentsByLocation = (Map<String, Integer>) jsonResult.get("accidentsByLocation");

            // Retrieve the counts for the specified reason
            return Map.of(reason, accidentsByLocation.get(reason));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AccidentReasonsService.class, args);
    }
}
