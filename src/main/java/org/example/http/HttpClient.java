package org.example.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpClient implements HttpClientInterface {
    @Override
    public String get(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        status >= 200 && status < 300 ?
                                connection.getInputStream() :
                                connection.getErrorStream(),
                        StandardCharsets.UTF_8
                )
        )) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (status < 200 || status >= 300) {
                throw new IOException("HTTP error " + status + ": " + response);
            }

            return response.toString();
        } finally {
            connection.disconnect();
        }
    }
}
