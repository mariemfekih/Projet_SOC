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
@RequestMapping("/accidentCountsByHour")
public class AccidentCountsByHourService {

    private static final String JSON_FILE_PATH = "accidentsByHour.json";

    @GetMapping("/getHours")
    public List<String> getAccidentHours() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, Map.class);

            // Assuming the hours are stored in the "accidentsByHour" key
            Map<String, Map<String, Integer>> accidentsByHour = (Map<String, Map<String, Integer>>) jsonResult.get("accidentsByHour");
            return new ArrayList<>(accidentsByHour.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return new ArrayList<>();
        }
    }

    @GetMapping("/getHours/{hour}")
    public Map<String, Integer> getAccidentCountsByHour(@PathVariable String hour) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, Map.class);

            // Assuming the hours are stored in the "accidentsByHour" key
            Map<String, Map<String, Integer>> accidentsByHour = (Map<String, Map<String, Integer>>) jsonResult.get("accidentsByHour");
            return accidentsByHour.get(hour);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AccidentCountsByHourService.class, args);
    }
}
