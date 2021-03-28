package com.example.hw05;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    Double temp, temp_min, temp_max, humidity, speed, deg, all;
    String description,icon;

    public Weather(JSONObject jsonObject) throws JSONException {
        JSONObject mainObj = jsonObject.getJSONObject("main");
        this.temp = mainObj.getDouble("temp");
        this.temp_min = mainObj.getDouble("temp_min");
        this.temp_max = mainObj.getDouble("temp_max");
        this.humidity = mainObj.getDouble("humidity");

        JSONObject windObj = jsonObject.getJSONObject("wind");
        this.speed = windObj.getDouble("speed");
        this.deg = windObj.getDouble("deg");

        JSONArray weatherArray = jsonObject.getJSONArray("weather");
        if(weatherArray.length() > 0) {
            this.description = weatherArray.getJSONObject(0).getString("description");
            this.icon = weatherArray.getJSONObject(0).getString("icon");
        }

        JSONObject cloudObj = jsonObject.getJSONObject("clouds");
        this.all = cloudObj.getDouble("all");
    }

    @Override
    public String toString() {
        return "Weather{" +
                "temp=" + temp +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                ", humidity=" + humidity +
                ", speed=" + speed +
                ", deg=" + deg +
                ", all=" + all +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
