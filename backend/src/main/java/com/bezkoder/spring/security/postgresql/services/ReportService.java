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

import java.net.URI;

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

        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders responseHeaders = response.getHeaders();
            // Obtener el valor de X-JSON del header
            String pdfUrl = responseHeaders.getFirst("X-JSON");
            // Extraer el nombre del archivo de la URL
            String fileName = pdfUrl.substring(pdfUrl.lastIndexOf('/') + 1);
            // Formatear la URL con el nombre del archivo
            String formattedUrl = "http://sismo.unsa.edu.pe/byt/reports?C=DL&f=" + fileName;
            return formattedUrl;
        } else {
            throw new RuntimeException("Failed to generate report");
        }
    }
}

