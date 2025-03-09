package com.api.donation_api.service;

import com.api.donation_api.dto.GeolocationResponseDTO;
import com.api.donation_api.model.Address;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeolocationService {

    private static final Logger logger = LoggerFactory.getLogger(GeolocationService.class);
    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;
    private final RateLimiter rateLimiter;

    public GeolocationService(WebClient.Builder webClientBuilder,
                              CircuitBreakerRegistry circuitBreakerRegistry,
                              RateLimiterRegistry rateLimiterRegistry,
                              @Value("${geolocation.api.url}") String geolocationApiUrl) {
        this.webClient = webClientBuilder
                .baseUrl(geolocationApiUrl)
                .build();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("nominatimService");
        this.rateLimiter = rateLimiterRegistry.rateLimiter("nominatimService");
    }

    public Mono<GeolocationResponseDTO> fetchGeolocation(Address address) {
        String searchQuery = String.format("%d+%s,+%s,+%s,+%s",
                address.getNumber() != null ? address.getNumber() : 0,
                address.getStreet().replaceAll("\\s+", "+"),
                address.getNeighborhood().replaceAll("\\s+", "+"),
                address.getCity().replaceAll("\\s+", "+"),
                address.getState().replaceAll("\\s+", "+")
        );

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("format", "jsonv2")
                        .queryParam("q", searchQuery)
                        .build())
                .header("User-Agent", "api-care-on/1.0 (deanvinicius2003@gmail.com)")
                .retrieve()
                .bodyToMono(GeolocationResponseDTO[].class)
                .flatMap(arr -> arr.length > 0 ? Mono.just(arr[0]) : Mono.empty())
                .transformDeferred(RateLimiterOperator.of(rateLimiter))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorResume(throwable -> {
                    logger.error("Erro na chamada de geolocalização: {}", throwable.getMessage());
                    return Mono.empty();
                });
    }
}

