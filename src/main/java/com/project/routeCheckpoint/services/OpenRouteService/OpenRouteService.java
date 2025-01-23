package com.project.routeCheckpoint.services.OpenRouteService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.routeCheckpoint.dto.CheckpointDTO;
import com.project.routeCheckpoint.dto.CoordinatesDTO;

import reactor.core.publisher.Mono;

@Service
public class OpenRouteService {
	
	private final WebClient webClient;

    @Value("${openrouteservice.api.key}")
    private String apiKey;
    
    public OpenRouteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openrouteservice.org").build();
    }
    
    public Mono<String> getWalkingRoute(CoordinatesDTO startCoordinates, CoordinatesDTO endCoordinates) {


    	String url = "/v2/directions/foot-walking" +
                "?api_key=" + apiKey +
                "&start=" + startCoordinates.getStartLongitude() + "," + startCoordinates.getStartLatitude() +
                "&end=" + endCoordinates.getEndLongitude() + "," + endCoordinates.getEndLatitude();
        
        return this.webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(String.class)
        .doOnNext(response -> {
            // Extraer los valores del summary
            try {
                // Usar un parser JSON para obtener los valores
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(response);
                JsonNode summaryNode = rootNode.path("features").get(0).path("properties").path("summary");

                double distance = summaryNode.path("distance").asDouble();
                double duration = summaryNode.path("duration").asDouble();

            } catch (Exception e) {
                System.out.println("Error parsing the route data: " + e.getMessage());
            }
        });
    }


}
