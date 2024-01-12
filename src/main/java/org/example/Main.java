package org.example;

import javax.xml.ws.Endpoint;

public class Main {
    public static void main(String[] args) {
        // Créer l'instance de votre service SOAP
        AccidentReasonsServiceSOAP service1 = new AccidentReasonsServiceSOAP();


        // Publier les services sur des URL spécifiques
        String address1 = "http://localhost:8080/accidentReasonsService";

        Endpoint.publish(address1, service1);


        // Afficher un message pour indiquer que les services ont été publiés avec succès
        System.out.println("Service published at: " + address1 + "?wsdl");

    }
}



//http://localhost:8080/accidentReasonsService?wsdl
//http://localhost:8080/accidentLocationService?wsdl