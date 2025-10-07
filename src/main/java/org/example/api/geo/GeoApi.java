package org.example.api.geo;

public interface GeoApi {
    /**
     * Получает координаты города (широту и долготу) по его названию.
     * @param city название города
     * @return массив [latitude, longitude]
     * @throws Exception если город не найден или произошла ошибка API
     */
    double[] getCoordinates(String city) throws Exception;
}