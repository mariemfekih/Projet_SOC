package org.example;

import org.json.JSONObject;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebService
public class AccidentLocationServiceSOAP {

    private Map<String, Integer> accidentsByLocation;

    public AccidentLocationServiceSOAP() {
        accidentsByLocation = loadAccidentsFromJson();
    }

    private Map<String, Integer> loadAccidentsFromJson() {
        Map<String, Integer> data = new HashMap<>();
        try {
            // Read JSON file content
            String jsonContent = new String(Files.readAllBytes(Paths.get("accidentsByLocation.json")));

            // Parse JSON data
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONObject accidentsByLocationJson = jsonObject.getJSONObject("accidentsByLocation");

            // Populate map with data from JSON
            accidentsByLocationJson.keys().forEachRemaining(location ->
                    data.put(location, accidentsByLocationJson.getInt(location)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @WebMethod
    public int getAccidentsByLocation(@WebParam(name = "location") String location) {
        return accidentsByLocation.getOrDefault(location, 0);
    }

    @WebMethod
    public int getTotalTues() {
        return 1092;
    }

    @WebMethod
    public int getTotalBlesses() {
        return 7017;
    }

    public static void main(String[] args) {
        AccidentLocationServiceSOAP service = new AccidentLocationServiceSOAP();
        String address = "http://localhost:8080/accidentLocationService";
        Endpoint.publish(address, service);
        System.out.println("Service published at: " + address + "?wsdl");
    }
}
