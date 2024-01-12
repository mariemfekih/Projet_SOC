import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class Operation2 {

    public static void main(String[] args) {
        Map<String, Object> jsonResult = new HashMap<>();  // Declare jsonResult outside the try block

        try {
            File file = new File("C:\\Users\\marie\\Downloads\\Projet soc-20231213T155927Z-001\\Projet soc\\gouvernorat2023.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("item");

            Map<String, Integer> accidentsByLocation = new HashMap<>();
            int totalTues = 0;
            int totalBlesses = 0;

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    String labelle = element.getElementsByTagName("labelle").item(0).getTextContent();
                    String accidents = element.getElementsByTagName("accidents").item(0).getTextContent();
                    String tues = element.getElementsByTagName("tues").item(0).getTextContent();
                    String blesses = element.getElementsByTagName("blesses").item(0).getTextContent();

                    // Update accidents count by location
                    accidentsByLocation.put(labelle, Integer.parseInt(accidents) + accidentsByLocation.getOrDefault(labelle, 0));

                    // Total Tue's and Blesses count
                    totalTues += Integer.parseInt(tues);
                    totalBlesses += Integer.parseInt(blesses);
                }
            }

            // Display aggregated accident counts by location
            for (Map.Entry<String, Integer> entry : accidentsByLocation.entrySet()) {
                System.out.println("Location: " + entry.getKey() + ", Accidents: " + entry.getValue());
            }

            // Display total Tue's and Blesses count
            System.out.println("Total Tues: " + totalTues);
            System.out.println("Total Blesses: " + totalBlesses);

            // Add data to jsonResult
            jsonResult.put("accidentsByLocation", accidentsByLocation);
            jsonResult.put("totalTues", totalTues);
            jsonResult.put("totalBlesses", totalBlesses);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Convert data to JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(jsonResult);
            System.out.println("JSON Result:\n" + jsonString);
            File outputFile = new File("accidentsByLocation.json");
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(jsonString);
            }

            System.out.println("JSON Result saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
