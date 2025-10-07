package org.example.api.geo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.http.HttpClientInterface;

public class OpenMeteoGeoApi implements GeoApi {
    private final HttpClientInterface httpClient;

    public OpenMeteoGeoApi(HttpClientInterface httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public double[] getCoordinates(String city) throws Exception {
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + city;
        String response = httpClient.get(url);

        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
        JsonArray results = json.getAsJsonArray("results");

        if (results == null || results.size() == 0) {
            throw new Exception("City not found: " + city);
        }

        JsonObject first = results.get(0).getAsJsonObject();
        return new double[]{
                first.get("latitude").getAsDouble(),
                first.get("longitude").getAsDouble()
        };
    }
}
