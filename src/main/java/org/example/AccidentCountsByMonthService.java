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
@RequestMapping("/accidentCountsByMonth")
public class AccidentCountsByMonthService {

    private static final String JSON_FILE_PATH = "accidentsByMonth.json";

    @GetMapping("/getMonths")
    public List<String> getAccidentMonths() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Map<String, Integer>> jsonResult = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Map<String, Integer>>>() {});

            // Assuming the months are stored as keys in the outer map
            return new ArrayList<>(jsonResult.keySet());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return new ArrayList<>();
        }
    }

    @GetMapping("/getMonths/{month}")
    public Map<String, Integer> getAccidentCountsByMonth(@PathVariable String month) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(JSON_FILE_PATH);
            Map<String, Map<String, Integer>> jsonResult = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Map<String, Integer>>>() {});

            // Assuming the months are stored as keys in the outer map
            return jsonResult.get(month);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(AccidentCountsByMonthService.class, args);
    }
}
