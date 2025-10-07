package org.example.api.weather;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.http.HttpClientInterface;

public class OpenMeteoApi implements WeatherApi {
    private final HttpClientInterface httpClient;
    public OpenMeteoApi (HttpClientInterface httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public JsonObject getWeatherByCoordinates(double[] coords) throws Exception {
        double lat = coords[0];
        double lon = coords[1];

        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat +
                "&longitude=" + lon + "&hourly=temperature_2m&forecast_days=1";

        String response = httpClient.get(url);
        return JsonParser.parseString(response).getAsJsonObject();
    }
}
