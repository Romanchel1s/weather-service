package org.example.server;

import org.example.api.geo.GeoApi;
import org.example.api.geo.OpenMeteoGeoApi;
import org.example.api.weather.OpenMeteoApi;
import org.example.api.weather.WeatherApi;
import org.example.controllers.WeatherController;
import org.example.http.HttpClient;
import org.example.http.HttpClientInterface;
import org.example.services.cache.ICacheService;
import org.example.services.cache.RedisCacheService;
import org.example.services.weather.IWeatherService;
import org.example.services.weather.WeatherService;

public class AppConfig {
    public static AppContext init() {
        AppContext context = new AppContext();

        context.register(HttpClientInterface.class, HttpClient::new);
        context.register(GeoApi.class, () -> new OpenMeteoGeoApi(context.get(HttpClientInterface.class)));
        context.register(WeatherApi.class, () -> new OpenMeteoApi(context.get(HttpClientInterface.class)));
        context.register(ICacheService.class, () -> new RedisCacheService("redis", 6379));
        context.register(IWeatherService.class, () -> new WeatherService(context.get(GeoApi.class),
                context.get(WeatherApi.class),
                context.get(ICacheService.class)));
        context.register(WeatherController.class, () -> new WeatherController(context.get(IWeatherService.class)));

        return context;
    }
}
