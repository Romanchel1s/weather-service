package org.example.http;


import java.io.IOException;

public interface HttpClientInterface {
    /**
     * Выполняет HTTP GET-запрос по указанному URL и возвращает тело ответа в виде строки.
     *
     * @param urlStr полный URL
     * @return тело ответа (response body)
     * @throws IOException если произошла ошибка соединения или сервер вернул код ошибки
     */
    String get(String urlStr) throws IOException;
}