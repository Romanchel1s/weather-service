package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.services.weather.IWeatherService;
import org.example.utils.HttpUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class WeatherController implements HttpHandler {
    private final IWeatherService weatherService;

    public WeatherController(IWeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        String city = null;
        if (query != null && query.startsWith("city=")) {
            city = query.substring(5);
        }

        if (city == null || city.isBlank()) {
            HttpUtils.sendJson(exchange, 400, "{\"error\":\"Missing 'city' parameter\"}");
            return;
        }

        // Декодируем, если пришёл %D0%9C%D0%BE%D1%81%D0%BA%D0%B2%D0%B0
        city = URLDecoder.decode(city, StandardCharsets.UTF_8);

        // Проверка — только английские буквы и пробелы
        if (!city.matches("^[A-Za-z\\s-]+$")) {
            HttpUtils.sendJson(exchange, 400, "{\"error\":\"City name must contain only English letters\"}");
            return;
        }

        try {
            String jsonResponse = weatherService.getWeather(city);
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
            exchange.getResponseBody().write(jsonResponse.getBytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            String response = "{\"error\":\"" + e.getMessage() + "\"}";
            exchange.sendResponseHeaders(500, response.length());
            exchange.getResponseBody().write(response.getBytes());
        }
        exchange.close();
    }
}
