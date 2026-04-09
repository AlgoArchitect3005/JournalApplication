package com.YashGPT.journalApp.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.YashGPT.journalApp.Dto.WeatherResponse;

import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeatherService {
    @Value("${Weather.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create();

    public WeatherResponse getWeather(String city){
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        log.info("Fetching weather data for city: {}", city);
        try {
            WeatherResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(WeatherResponse.class)
                    .block();
             log.info("Weather data fetched successfully for city: {}", city);
            return response;
        } catch(WebClientResponseException e) {
            log.error("HTTP error fetching weather for city: {}, Status: {}", city, e.getStatusCode());
            throw new RuntimeException("Weather service error: " + e.getStatusCode());
        }
        catch (Exception e) {
            log.error("Unexpected error fetching weather for city: {}", city);
            throw new RuntimeException("Weather data fetch failed for city: " + city);
        }
    }


}
