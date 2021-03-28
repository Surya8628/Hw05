package com.example.hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentWeatherFragment extends Fragment {
final String TAG="CurrentWeather";
    private static final String ARG_CITY = "city";
    static Data.City place;
    TextView placename, temp, temp_min, temp_max, wind_speed, wind_degree, description, cloudiness, humidity;
    ImageView icon;
    Button checkForecast;
    final OkHttpClient client = new OkHttpClient();
    private Data.City SelectedCity;

    public CurrentWeatherFragment(Data.City city) {
        this.SelectedCity=city;

    }

    public static CurrentWeatherFragment newInstance(Data.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment(city);
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            SelectedCity = (Data.City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_current_weather, container, false);
        getActivity().setTitle(getResources().getString(R.string.CurrentWeather));
        placename = view.findViewById(R.id.Places);
        Log.d(TAG, "onCreateView: "+ SelectedCity);
        placename.setText(SelectedCity.getCity());
        temp = view.findViewById(R.id.Temperature);
        temp_max = view.findViewById(R.id.TempMax);
        temp_min = view.findViewById(R.id.TempMin);
        description = view.findViewById(R.id.Desc);
        humidity = view.findViewById(R.id.humidity);
        wind_speed = view.findViewById(R.id.WindSpeed);
        wind_degree = view.findViewById(R.id.WindDegree);
        cloudiness = view.findViewById(R.id.Cloud);
        checkForecast = view.findViewById(R.id.CheckForecast);
        icon = (ImageView) view.findViewById(R.id.icon);
       checkForecast.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                mListener.goToWeatherForecast(SelectedCity);
           }
       });
        getCurrentWeather();
        return view;
    }
    private void getCurrentWeather() {
        HttpUrl httpUrl = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addEncodedQueryParameter("q",String.valueOf(SelectedCity.getCity()))
                .addEncodedQueryParameter("appid", getResources().getString(R.string.API_KEY))
                .addEncodedQueryParameter("units","imperial")
                .addQueryParameter("mode","json")
                .build();

        Request request = new Request.Builder().url(httpUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {



                    JSONObject jsonObject = new JSONObject(response.body().string());
                     Weather weather = new Weather(jsonObject);
                    Log.d(TAG, "onResponse: "+weather);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            temp.setText(weather.temp+getResources().getString(R.string.F));
                            temp_max.setText(weather.temp_max+ getResources().getString(R.string.F));
                            temp_min.setText(weather.temp_min+getResources().getString(R.string.F));
                            humidity.setText(weather.humidity+getResources().getString(R.string.percentage));
                            wind_speed.setText(weather.speed+getResources().getString(R.string.miles));
                            wind_degree.setText(weather.deg+getResources().getString(R.string.degrees));
                            description.setText(weather.description);
                            cloudiness.setText(weather.all+getResources().getString(R.string.percentage));
                            String path = "http://openweathermap.org/img/wn/"+weather.icon+"@2x.png";
                            Picasso.get().load(path).into(icon);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    CurrentWeatherListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CurrentWeatherListener) context;
    }
    public interface CurrentWeatherListener{
        void goToWeatherForecast(Data.City city);
    }
}