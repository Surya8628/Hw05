package com.example.hw05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherForecastFragment extends Fragment {
final String TAG="weatherForecast";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CITY = "city";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView placeName;
    private RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private WeatherForeCastAdapter weatherForeCastAdapter;
    ArrayList<WeatherForecastModel> weatherList = new ArrayList<>();
    final OkHttpClient client = new OkHttpClient();
    // TODO: Rename and change types of parameters
    private Data.City selectedCity;

    public WeatherForecastFragment(Data.City city) {
        this.selectedCity=city;
        // Required empty public constructor
    }


    public static WeatherForecastFragment newInstance(Data.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment(city);
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedCity = (Data.City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_weather_forecast, container, false);
        getActivity().setTitle(getResources().getString(R.string.WeatherForecast));
        getWeatherForecast();
        placeName = view.findViewById(R.id.dt_txt);
        placeName.setText(String.valueOf(selectedCity.getCity()));
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Log.d(TAG, "onCreateView: "+weatherList);

        weatherForeCastAdapter = new WeatherForeCastAdapter(weatherList);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        
        recyclerView.setAdapter(weatherForeCastAdapter);
        return view;
    }
    public void getWeatherForecast(){
        HttpUrl httpUrl = HttpUrl.parse("https://api.openweathermap.org/data/2.5/forecast").newBuilder()
                .addEncodedQueryParameter("q",String.valueOf(selectedCity.getCity()))
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
                    Log.d(TAG, "onResponse: "+jsonObject);

                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); ++i){
                        JSONObject obj = jsonArray.getJSONObject(i);
                        WeatherForecastModel weather = new WeatherForecastModel(obj);
                        weatherList.add(weather);

                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            weatherForeCastAdapter.notifyDataSetChanged();
                        }
                    });
                    Log.d(TAG, "onResponse: "+weatherList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}