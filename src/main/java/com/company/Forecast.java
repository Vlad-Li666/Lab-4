package com.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    private Location location;
    private ForecastDays forecast;

    public ForecastDays getForecast() {
        return forecast;
    }

    public void setForecast(ForecastDays forecast) {
        this.forecast = forecast;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast() {
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "  location=" + location + "\n" +
                "  forecast=" + forecast +
                '}';
    }

    public static Forecast getWeatherForecastByURL(String urlStr) throws IOException {
        String encodedURL = encodeUrl(urlStr);
        URL url = new URL(encodedURL);

        StringBuilder stringBuilder = new StringBuilder();
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        try (InputStream inputStreamReader = connection.getInputStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamReader, StandardCharsets.UTF_8));

            int ch;
            while ((ch = bufferedReader.read()) != -1) {
                stringBuilder.append((char) ch);
            }
            System.out.println(stringBuilder.toString());

        }

        return new ObjectMapper().readValue(stringBuilder.toString(), Forecast.class);
    }

    private static String encodeUrl(String urlStr) throws IOException {
        URI uri;
        try {
            uri = new URI(urlStr);
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        return uri.toASCIIString();
    }

}
