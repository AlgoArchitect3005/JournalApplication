package com.YashGPT.journalApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.YashGPT.journalApp.Service.WeatherService;
import com.YashGPT.journalApp.Dto.WeatherResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        log.info("Weather request received for city: {}", city);
        try {
            WeatherResponse response = weatherService.getWeather(city);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Failed to fetch weather for city: {}", city);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }
    }
}
