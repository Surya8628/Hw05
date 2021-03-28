package com.example.hw05;


import android.view.LayoutInflater;
import android.view.View;import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.TextView;

 import androidx.annotation.NonNull;
 import androidx.recyclerview.widget.RecyclerView;

 import com.squareup.picasso.Picasso;
 import java.util.ArrayList;

public class WeatherForeCastAdapter extends RecyclerView.Adapter<WeatherForeCastAdapter.ViewHolder> {

    ArrayList<WeatherForecastModel> weatherForecast;
    private static final String TAG = "WeatherForecatAdapter";
    public WeatherForeCastAdapter(ArrayList<WeatherForecastModel> weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast_card_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dt.setText(weatherForecast.get(position).dt_txt);
        holder.temp.setText(weatherForecast.get(position).weather.temp+" F");
        holder.temp_max.setText(holder.itemView.getContext().getString(R.string.Max)+weatherForecast.get(position).weather.temp_max+"F");
        holder.temp_min.setText(holder.itemView.getContext().getString(R.string.Min)+weatherForecast.get(position).weather.temp_min+"F");
        holder.humidity.setText(holder.itemView.getContext().getString(R.string.Humidity)+weatherForecast.get(position).weather.humidity+"%");
        holder.desc.setText(weatherForecast.get(position).weather.description);
        String path = "http://openweathermap.org/img/wn/"+weatherForecast.get(position).weather.icon+"@2x.png";
        Picasso.get().load(path).into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return weatherForecast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dt,temp,temp_max,temp_min,humidity,desc;
        ImageView icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dt = itemView.findViewById(R.id.dt_txt);
            temp = itemView.findViewById(R.id.Temp);
            temp_max = itemView.findViewById(R.id.MaxTemp);
            temp_min = itemView.findViewById(R.id.MinTemp);
            humidity = itemView.findViewById(R.id.Humidity);
            desc = itemView.findViewById(R.id.description);
            icon = itemView.findViewById(R.id.icon);
        }
    }
}
