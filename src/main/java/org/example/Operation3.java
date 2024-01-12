import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Operation3 {

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Users\\marie\\Downloads\\Projet soc-20231213T155927Z-001\\Projet soc\\heure2023.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");

            Map<String, Map<String, Integer>> accidentsByHour = new HashMap<>();
            int totalAccidents = 0;
            int totalTues = 0;
            int totalBlesses = 0;

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    String hour = element.getElementsByTagName("labelle").item(0).getTextContent();
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

                    accidentsByHour.put(hour, data);
                }
            }

            // Display aggregated accident counts by hour
            for (Map.Entry<String, Map<String, Integer>> entry : accidentsByHour.entrySet()) {
                System.out.println("Hour: " + entry.getKey());
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

            // Create JSON data
            Map<String, Object> jsonResult = new HashMap<>();
            jsonResult.put("accidentsByHour", accidentsByHour);
            jsonResult.put("totalAccidents", totalAccidents);
            jsonResult.put("totalTues", totalTues);
            jsonResult.put("totalBlesses", totalBlesses);

            // Convert data to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(jsonResult);

            // Save JSON to a file
            File outputFile = new File("accidentsByHour.json");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(jsonString);
            }

            System.out.println("JSON Result saved to: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
