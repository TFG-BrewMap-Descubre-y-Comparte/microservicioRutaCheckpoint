package com.project.routeCheckpoint.services.OpenRouteService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class OpenRouteService {
	
	private final WebClient webClient;

    @Value("${openrouteservice.api.key}")
    private String apiKey;
    
    public OpenRouteService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openrouteservice.org").build();
    }

}
