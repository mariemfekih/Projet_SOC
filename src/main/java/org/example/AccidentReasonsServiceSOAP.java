package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@WebService
public class AccidentReasonsServiceSOAP {

    private Map<String, AccidentDetails> accidentDetailsMap;

    public AccidentReasonsServiceSOAP() {
        accidentDetailsMap = loadAccidentsFromJson();
    }

    private Map<String, AccidentDetails> loadAccidentsFromJson() {
        Map<String, AccidentDetails> data = new HashMap<>();
        try {
            // Replace "your_path/accidentsByReason.json" with the actual path to your JSON file
            String jsonFilePath = "accidentsByReason.json";

            // Read JSON file content
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(jsonFilePath);
            Map<String, Object> jsonResult = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Object>>() {});

            // Get total counts
            int totalTues = (int) jsonResult.get("totalTues");
            int totalBlesses = (int) jsonResult.get("totalBlesses");

            // Get the map of accidents by reason
            Map<String, Integer> accidentsByReason = (Map<String, Integer>) jsonResult.get("accidentsByReason");

            // Populate map with data from JSON
            accidentsByReason.forEach((label, count) ->
                    data.put(label, new AccidentDetails(count, totalTues, totalBlesses)));

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
        return data;
    }

    @WebMethod
    public AccidentDetails getAccidentData(@WebParam(name = "labelle") String labelle) {
        return accidentDetailsMap.getOrDefault(labelle, new AccidentDetails(0, 0, 0));
    }

    @WebMethod
    public AccidentDetails getTotalCounts() {
        return accidentDetailsMap.get("Total");
    }

    // Class to represent accident details
    private static class AccidentDetails {
        private int accidents;
        private int tues;
        private int blesses;

        public AccidentDetails(int accidents, int tues, int blesses) {
            this.accidents = accidents;
            this.tues = tues;
            this.blesses = blesses;
        }

        public int getAccidents() {
            return accidents;
        }

        public void setAccidents(int accidents) {
            this.accidents = accidents;
        }

        public int getTues() {
            return tues;
        }

        public void setTues(int tues) {
            this.tues = tues;
        }

        public int getBlesses() {
            return blesses;
        }

        public void setBlesses(int blesses) {
            this.blesses = blesses;
        }
    }

    public static void main(String[] args) {
        AccidentReasonsServiceSOAP service = new AccidentReasonsServiceSOAP();
        String address = "http://localhost:8080/accidentReasonsService";
        Endpoint.publish(address, service);
        System.out.println("Service published at: " + address + "?wsdl");
    }
}
