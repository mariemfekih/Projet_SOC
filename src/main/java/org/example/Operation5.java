package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Operation5 {

    private static final String JSON_FILE_PATH = "accidentsByRoadType.json";

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\marie\\Downloads\\Projet soc-20231213T155927Z-001\\Projet soc\\route2023.xml");
            Map<String, Map<String, Integer>> accidentsByRoadType = processXmlFile(file);

            // Display aggregated accident counts by road type
            displayAccidentData(accidentsByRoadType);

            // Write data to JSON file
            writeToJsonFile(accidentsByRoadType, JSON_FILE_PATH);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Map<String, Integer>> processXmlFile(File file) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        org.w3c.dom.Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        org.w3c.dom.NodeList nodeList = doc.getElementsByTagName("item");

        Map<String, Map<String, Integer>> accidentsByRoadType = new HashMap<>();
        int totalAccidents = 0;
        int totalTues = 0;
        int totalBlesses = 0;

        for (int temp = 0; temp < nodeList.getLength(); temp++) {
            org.w3c.dom.Node node = nodeList.item(temp);
            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                String roadType = element.getElementsByTagName("labelle").item(0).getTextContent();
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

                accidentsByRoadType.put(roadType, data);
            }
        }

        // Display total accidents, tues, and blesses
        System.out.println("Total Accidents: " + totalAccidents);
        System.out.println("Total Tues: " + totalTues);
        System.out.println("Total Blesses: " + totalBlesses);

        return accidentsByRoadType;
    }

    private static void displayAccidentData(Map<String, Map<String, Integer>> accidentData) {
        // Display aggregated accident counts by road type
        for (Map.Entry<String, Map<String, Integer>> entry : accidentData.entrySet()) {
            System.out.println("Road Type: " + entry.getKey());
            Map<String, Integer> data = entry.getValue();
            for (Map.Entry<String, Integer> innerEntry : data.entrySet()) {
                System.out.println(innerEntry.getKey() + ": " + innerEntry.getValue());
            }
            System.out.println("---------------");
        }
    }

    private static void writeToJsonFile(Map<String, Map<String, Integer>> data, String filePath) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(data);
            File outputFile = new File(filePath);
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(jsonString);
            }

            System.out.println("JSON Result saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
