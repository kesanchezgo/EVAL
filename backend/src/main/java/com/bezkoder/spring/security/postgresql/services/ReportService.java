package com.bezkoder.spring.security.postgresql.services;

/* import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReportService {

    @Value("${report.endpoint}")
    private String reportEndpoint;

    public String generateReport(Long projectId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"idproyectoinforme\": " + projectId + "}";

        System.out.println(requestBody);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                reportEndpoint,
                HttpMethod.POST,
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders responseHeaders = response.getHeaders();
            String pdfUrl = responseHeaders.getFirst("X-JSON");
            return pdfUrl;
        } else {
            throw new RuntimeException("Failed to generate report");
        }
    }
}
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URI;
import java.util.Map;

@Service
public class ReportService {

    @Value("${report.endpoint}")
    private String reportEndpoint;

    public String generateReport(Long projectId) {
        HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir el cuerpo del mensaje con el nuevo parámetro
        String requestBody = "{\"idproyectoinforme\": " + projectId + "}";
        System.out.println(requestBody);
        
        // Ajustar la URL del endpoint con los nuevos parámetros
        URI uri = UriComponentsBuilder.fromHttpUrl(reportEndpoint)
                .queryParam("outputtype", "PDF")
                .queryParam("reportname", "informe-final")
                .queryParam("paramstoreport", "{\"idproyectoinforme\": " + projectId + "}")
                .build()
                .toUri();

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                String.class
        );

        /* if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders responseHeaders = response.getHeaders();
            // Obtener el valor de X-JSON del header
            String pdfUrl = responseHeaders.getFirst("X-JSON");
            System.out.println(pdfUrl);
            // Extraer el nombre del archivo de la URL
            String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);
            // Formatear la URL con el nombre del archivo
            String formattedUrl = "http://sismo.unsa.edu.pe/byt/reports?C=DL&f=" + fileName;
            System.out.println(fileName);
            return formattedUrl;
        } else {
            throw new RuntimeException("Failed to generate report");
        } */
        // Dentro de tu método o función donde estás manejando la respuesta
        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders responseHeaders = response.getHeaders();
            // Obtener el valor de X-JSON del header
            String jsonValue = responseHeaders.getFirst("X-JSON");
            
            try {
                // Parsear el JSON para obtener el nombre del archivo
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> jsonMap = objectMapper.readValue(jsonValue, Map.class);
                String fileName = (String) jsonMap.get("urlFile");

                // Formatear la URL con el nombre del archivo
                String formattedUrl = "http://sismo.unsa.edu.pe/byt/reports?C=DL&f=" + fileName;
                return formattedUrl;
            } catch (JsonProcessingException e) {
                // Manejar la excepción aquí
                e.printStackTrace(); // O utiliza algún otro método apropiado de manejo de errores
                throw new RuntimeException("Failed to parse JSON response", e);
            }
        } else {
            throw new RuntimeException("Failed to generate report");
        }
        
    }
}

