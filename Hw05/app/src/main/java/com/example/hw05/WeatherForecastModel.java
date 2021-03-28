package com.example.hw05;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherForecastModel {
    Weather weather;
    String dt_txt;

    public WeatherForecastModel(JSONObject obj) throws JSONException {
        this.weather = new Weather(obj);
        this.dt_txt = obj.getString("dt_txt");
    }

    @Override
    public String toString() {
        return "WeatherForecastModel{" +
                "weather=" + weather +
                ", dt_txt='" + dt_txt + '\'' +
                '}';
    }
}
