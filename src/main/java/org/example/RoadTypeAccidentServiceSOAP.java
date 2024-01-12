package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.ws.Endpoint;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@WebService
public class RoadTypeAccidentServiceSOAP {

    private Map<String, AccidentDetails> roadTypeAccidentDetailsMap;

    public RoadTypeAccidentServiceSOAP() {
        roadTypeAccidentDetailsMap = loadAccidentsFromJson();
    }

    private Map<String, AccidentDetails> loadAccidentsFromJson() {
        Map<String, AccidentDetails> data = new HashMap<>();
        try {
            // Replace "your_path/accidentsByRoadType.json" with the actual path to your JSON file
            String jsonFilePath = "accidentsByRoadType.json";

            // Read JSON file content
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(jsonFilePath);
            Map<String, Map<String, Integer>> jsonResult = objectMapper.readValue(jsonFile, new TypeReference<Map<String, Map<String, Integer>>>() {});

            // Populate map with data from JSON
            jsonResult.forEach((roadType, counts) ->
                    data.put(roadType, new AccidentDetails(counts.get("Accidents"), counts.get("Tues"), counts.get("Blesses"))));

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
        return data;
    }

    @WebMethod
    public AccidentDetails getRoadTypeAccidentData(@WebParam(name = "roadType") String roadType) {
        return roadTypeAccidentDetailsMap.getOrDefault(roadType, new AccidentDetails(0, 0, 0));
    }

    @WebMethod
    public AccidentDetails getTotalCounts() {
        return roadTypeAccidentDetailsMap.get("Total");
    }

    // AccidentDetails class
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AccidentDetails {
        private int accidents;
        private int tues;
        private int blesses;

        public AccidentDetails() {
            // Default constructor needed for JAXB
        }

        public AccidentDetails(int accidents, int tues, int blesses) {
            this.accidents = accidents;
            this.tues = tues;
            this.blesses = blesses;
        }

        // Getters and Setters
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
        RoadTypeAccidentServiceSOAP service = new RoadTypeAccidentServiceSOAP();

        // Publish the service on a specific URL
        String address = "http://localhost:8080/roadTypeAccidentService";

        Endpoint endpoint = Endpoint.publish(address, service);

        // Get the WSDL URL
        String wsdlURL = address + "?wsdl";

        // Display the WSDL URL
        System.out.println("WSDL for RoadTypeAccidentServiceSOAP: " + wsdlURL);

        // To stop the service (optional)
        // endpoint.stop();
    }
}
