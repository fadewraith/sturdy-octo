package com.ai.gemini;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service

public class QnaService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final WebClient webClient;

//    public QnaService(WebClient webClient) {
//        this.webClient = webClient;
//    }

    public QnaService(WebClient.Builder webClient) {
        this.webClient = webClient.build();
    }

    public String getAnswer(String question) {
        // {"contents": [{"parts": [{"text":"Hello, world!"}]}]}}
//        construct the request payload
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of(
                                "parts", new Object[] {
                                        Map.of(
                                                "text", question
                                        )
                                }
                        )
                }
        );

        // make api call
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // block is converting the reactive mono into a blocking call to get the actual string response

        // return respone
        return response;
    }
}
