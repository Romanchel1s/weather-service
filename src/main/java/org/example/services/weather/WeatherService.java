package org.example.services.weather;

import com.google.gson.JsonObject;
import org.example.api.geo.GeoApi;
import org.example.api.weather.WeatherApi;
import org.example.services.cache.ICacheService;

public class WeatherService implements IWeatherService {
    private final GeoApi geoApi;
    private final WeatherApi weatherApi;
    private final ICacheService cache;

    public WeatherService(GeoApi geoApi, WeatherApi weatherApi, ICacheService cache) {
        this.geoApi = geoApi;
        this.weatherApi = weatherApi;
        this.cache = cache;
    }

    @Override
    public String getWeather(String city) throws Exception {
        String cached = cache.get(city);
        if (cached != null) {
            System.out.println("✅ Cache hit for " + city);
            return cached;
        }

        System.out.println("🌐 Cache miss for " + city + " — fetching from API...");
        double[] coords = geoApi.getCoordinates(city);
        JsonObject weatherData = weatherApi.getWeatherByCoordinates(coords);

        String json = weatherData.toString();
        cache.set(city, json, 15 * 60); // 15 минут TTL

        return json;
    }
}
