package org.example.api.weather;
import com.google.gson.JsonObject;

public interface WeatherApi {
    /**
     * Получает прогноз погоды на ближайшие 24 часа по координатам.
     */
    JsonObject getWeatherByCoordinates(double[] coords) throws Exception;
}

