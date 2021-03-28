package com.example.hw05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener ,CurrentWeatherFragment.CurrentWeatherListener{
final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.containerView,new CitiesFragment()).commit();

    }

    @Override
    public void goToCurrentWeatherFragment(Data.City city) {
        Log.d(TAG, "goToCurrentWeatherFragment: "+city);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new CurrentWeatherFragment(city)).addToBackStack(null).commit();

    }

    @Override
    public void goToWeatherForecast(Data.City city) {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new WeatherForecastFragment(city)).addToBackStack(null).commit();

    }
}