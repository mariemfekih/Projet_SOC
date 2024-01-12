package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Operation4 {

    private static final String JSON_FILE_PATH = "accidentsByMonth.json";

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\marie\\Downloads\\Projet soc-20231213T155927Z-001\\Projet soc\\mois2023.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Map<String, Map<String, Integer>> accidentsByMonth = new HashMap<>();
            int totalAccidents = 0;
            int totalTues = 0;
            int totalBlesses = 0;

            for (int temp = 0; temp < dBuilder.parse(file).getElementsByTagName("item").getLength(); temp++) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) dBuilder.parse(file).getElementsByTagName("item").item(temp);
                String month = element.getElementsByTagName("labelle").item(0).getTextContent();
                String accidents = element.getElementsByTagName("accidents").item(0).getTextContent();
                String tues = element.getElementsByTagName("tues").item(0).getTextContent();
                String blesses = element.getElementsByTagName("blesses").item(0).getTextContent();

                int accidentsCount = Integer.parseInt(accidents);
                int tuesCount = Integer.parseInt(tues);
                int blessesCount = Integer.parseInt(blesses);

                totalAccidents += accidentsCount;
                totalTues += tuesCount;
                totalBlesses += blessesCount;

                Map<String, Integer> data = new HashMap<>();
                data.put("Accidents", accidentsCount);
                data.put("Tues", tuesCount);
                data.put("Blesses", blessesCount);

                accidentsByMonth.put(month, data);
            }

            // Display aggregated accident counts by month
            for (Map.Entry<String, Map<String, Integer>> entry : accidentsByMonth.entrySet()) {
                System.out.println("Month: " + entry.getKey());
                Map<String, Integer> data = entry.getValue();
                for (Map.Entry<String, Integer> innerEntry : data.entrySet()) {
                    System.out.println(innerEntry.getKey() + ": " + innerEntry.getValue());
                }
                System.out.println("---------------");
            }

            // Display total accidents, tues, and blesses
            System.out.println("Total Accidents: " + totalAccidents);
            System.out.println("Total Tues: " + totalTues);
            System.out.println("Total Blesses: " + totalBlesses);

            // Save data to JSON file
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File(JSON_FILE_PATH), accidentsByMonth);
            System.out.println("JSON Result saved to: " + JSON_FILE_PATH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
