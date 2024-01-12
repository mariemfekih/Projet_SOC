package org.example;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;
import javax.xml.ws.Endpoint;

@WebService
public class AccidentHourlyServiceSOAP {

    private Map<String, AccidentDetails> hourlyAccidentDetailsMap;

    public AccidentHourlyServiceSOAP() {
        hourlyAccidentDetailsMap = new HashMap<>();

        // Ajoutez les détails d'accident par heure ici

        addHourlyAccidentDetails("02-00", 158, 55, 248);
        addHourlyAccidentDetails("16-14", 547, 116, 785);
        addHourlyAccidentDetails("14-12", 607, 102, 853);
        addHourlyAccidentDetails("20-18", 578, 127, 838);
        addHourlyAccidentDetails("12-10", 588, 96, 787);
        addHourlyAccidentDetails("22-20", 485, 114, 661);
        addHourlyAccidentDetails("24-22", 238, 52, 353);
        addHourlyAccidentDetails("08-06", 383, 105, 512);
        addHourlyAccidentDetails("06-04", 168, 75, 260);
        addHourlyAccidentDetails("04-02", 114, 58, 158);
        addHourlyAccidentDetails("18-16", 687, 118, 902);
        addHourlyAccidentDetails("10-08", 514, 74, 660);


        // Ajoutez les données totales
        addTotalCounts(5067, 1092, 7017);
    }

    private void addHourlyAccidentDetails(String hour, int accidents, int tues, int blesses) {
        hourlyAccidentDetailsMap.put(hour, new AccidentDetails(accidents, tues, blesses));
    }

    private void addTotalCounts(int totalAccidents, int totalTues, int totalBlesses) {
        hourlyAccidentDetailsMap.put("Total", new AccidentDetails(totalAccidents, totalTues, totalBlesses));
    }

    @WebMethod
    public AccidentDetails getHourlyAccidentData(@WebParam(name = "hour") String hour) {
        return hourlyAccidentDetailsMap.getOrDefault(hour, new AccidentDetails(0, 0, 0));
    }

    @WebMethod
    public AccidentDetails getTotalCounts() {
        return hourlyAccidentDetailsMap.get("Total");
    }

    // Classe pour représenter les détails d'un accident par heure
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class AccidentDetails {
        private int accidents;
        private int tues;
        private int blesses;

        public AccidentDetails() {
            // Constructeur par défaut nécessaire pour JAXB
        }

        public AccidentDetails(int accidents, int tues, int blesses) {
            this.accidents = accidents;
            this.tues = tues;
            this.blesses = blesses;
        }

        // Getters et Setters
        // Assurez-vous que chaque attribut a un Getter et un Setter public
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
            AccidentHourlyServiceSOAP service = new AccidentHourlyServiceSOAP();

            // Publish the service on a specific URL
            String address = "http://localhost:8080/accidentHourlyService";

            Endpoint endpoint = Endpoint.publish(address, service);

            // Get the WSDL URL
            String wsdlURL = address + "?wsdl";

            // Display the WSDL URL
            System.out.println("WSDL for AccidentHourlyServiceSOAP: " + wsdlURL);

            // To stop the service (optional)
            // endpoint.stop();
        }
    }



//http://localhost:8080/accidentHourlyService?wsdl

