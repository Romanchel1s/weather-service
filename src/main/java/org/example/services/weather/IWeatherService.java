package org.example.services.weather;

public interface IWeatherService {
    /**
     * Возвращает JSON-прогноз погоды для указанного города.
     */
    String getWeather(String city) throws Exception;
}
