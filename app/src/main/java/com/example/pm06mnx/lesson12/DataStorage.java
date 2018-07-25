package com.example.pm06mnx.lesson12;

import com.example.pm06mnx.lesson12.list.dto.DayWeather;

import java.util.List;

/**
 * Временное хранилище прогнозов
 */
public enum DataStorage {
    INSTANCE;

    private List<DayWeather> forecast;

    public void setForecast(List<DayWeather> forecast) {
        this.forecast = forecast;
    }

    public List<DayWeather> getForecast() {
        return forecast;
    }
}
