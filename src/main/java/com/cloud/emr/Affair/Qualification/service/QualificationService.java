package com.cloud.emr.Affair.Qualification.service;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class QualificationService {

    private final WebClient webClient;

    private static final String MOCK_API_URL = "http://localhost:8085/mock/insurance/eligibility";

    public QualificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(MOCK_API_URL).build();
    }

    public Mono<Map<String, Object>> getHealthInsuranceInfo(String targetPatientRrn) {
        return webClient.post()
                .uri("/health")
                .bodyValue(Map.of("patientRrn", targetPatientRrn))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.empty(); // 404일 경우 Mono.empty() 반환
                    }
                    return response.createException().flatMap(Mono::error);
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Mono<Map<String, Object>> getMedicalAssistanceInfo(String targetPatientRrn) {
        return webClient.post()
                .uri("/medical-assistance")
                .bodyValue(Map.of("patientRrn", targetPatientRrn))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.empty(); // 404일 경우 Mono.empty() 반환
                    }
                    return response.createException().flatMap(Mono::error);
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

    public Mono<Map<String, Object>> getBasicLivelihoodInfo(String targetPatientRrn) {
        return webClient.post()
                .uri("/basic-livelihood")
                .bodyValue(Map.of("patientRrn", targetPatientRrn))
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    if (response.statusCode().equals(HttpStatus.NOT_FOUND)) {
                        return Mono.empty(); // 404일 경우 Mono.empty() 반환
                    }
                    return response.createException().flatMap(Mono::error);
                })
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {});
    }
}
