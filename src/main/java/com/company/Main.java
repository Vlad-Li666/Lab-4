package com.company;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Прогноз погоды(получение из json колекцию):");
        Forecast forecast = Forecast.getWeatherForecastByURL("https://api.weatherapi.com/v1/forecast.json?key=f720c35b125e4c18a38191356201110&q=Одесса&days=3");
        System.out.println(forecast);

        System.out.println("Прогноз погоды(сортировка):");
        forecast.getForecast().getForecastDays().sort(ForecastDay.byMaxtemp_c);
        System.out.println(forecast);

        System.out.println("Прогноз погоды(фильтрация):");
        List<Day> days = forecast.getForecast().filterByAvgTempGreaterThan(14.2);
        System.out.println(days);

    }
}
