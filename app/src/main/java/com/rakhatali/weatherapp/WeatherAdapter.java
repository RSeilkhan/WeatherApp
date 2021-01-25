package com.rakhatali.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.rakhatali.weatherapp.MainActivity.SHARED_PREFS;

public class WeatherAdapter extends  RecyclerView.Adapter<WeatherAdapter.ViewHolder>{

    private Context mContext;
    private List<Weather> weatherList;
    private OnWeatherListener mOnWeatherListener;
    String icon = "";
    public WeatherAdapter(Context context, List<Weather> weathers, OnWeatherListener onWeatherListener) {
        this.mContext = context;
        this.weatherList = weathers;
        this.mOnWeatherListener = onWeatherListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.weather_item, parent, false);
        return new WeatherAdapter.ViewHolder(view, mOnWeatherListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        Weather weather = weatherList.get(position);
        holder.city.setText(weather.getCity());
        holder.added_time.setText(formatter.format(weather.getAdded_time()));
        holder.temperature.setText(String.valueOf(weather.getTemperature()));
        icon = setWeatherIcon(weather.getDescription());
        Glide
                .with(mContext)
                .load(icon)
                .centerCrop()
                .into(holder.image);

        // "SharedPreferences" as example of local storage
//
//        Gson gson = new Gson();
//        SharedPreferences mPrefs = mContext.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
//        String json = mPrefs.getString("weatherData", "");
//        if (json.isEmpty()) {
//            Toast.makeText(mContext,"There is some error",Toast.LENGTH_LONG).show();
//        } else {
//            Type type = new TypeToken<List<Weather>>() {
//            }.getType();
//            List<Weather> weathers = gson.fromJson(json, type);
//            for(Weather weather : weathers) {
//                holder.city.setText(weather.getCity());
//                holder.added_time.setText(formatter.format(weather.getAdded_time()));
//                holder.temperature.setText(String.valueOf(weather.getTemperature()));
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView city, added_time, temperature;
        OnWeatherListener onWeatherListener;
        ImageView image;

        public ViewHolder(@NonNull View itemView, OnWeatherListener onWeatherListener) {
            super(itemView);
            image = itemView.findViewById(R.id.weather_image);
            city = itemView.findViewById(R.id.city_value);
            added_time = itemView.findViewById(R.id.time_value);
            temperature = itemView.findViewById(R.id.temperature_value);
            this.onWeatherListener = onWeatherListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onWeatherListener.onWeatherClick(getAdapterPosition(), image, icon);
        }
    }

    public interface OnWeatherListener{
        void onWeatherClick(int position, ImageView image, String icon);
    }

    private String setWeatherIcon(String desc){
        String backgroundImage = "";
        switch(desc) {
            case "clouds" : backgroundImage = "https://image.flaticon.com/icons/png/512/252/252035.png";
                break;
            case "main" : backgroundImage = "https://icons-for-free.com/iconfiles/png/512/clouds+rain+rainy+weather+icon-1320196492721656738.png";
                break;
            case "snow" : backgroundImage = "https://cdn.onlinewebfonts.com/svg/img_535543.png";
                break;
            case "clear" : backgroundImage = "https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_3-512.png";
                break;
            case "mist" : backgroundImage = "https://image.flaticon.com/icons/png/512/175/175867.png";
                break;
            case "thunderstorm" : backgroundImage = "https://image.flaticon.com/icons/png/512/17/17785.png";
                break;
            case "smoke" : backgroundImage = "https://cdn3.iconfinder.com/data/icons/weather-ios-11-black-white/50/Smoke_Smog_Low_visibility_Apple_Flat_iOS_Weather-512.png";
                break;
            case "haze" : backgroundImage = "https://image.flaticon.com/icons/png/512/182/182266.png";
                break;
            case "fog" : backgroundImage = "https://icons-for-free.com/iconfiles/png/512/cloudy+fog+foggy+weather+icon-1320196634478143974.png";
                break;
            default : backgroundImage = "https://i.pinimg.com/originals/77/0b/80/770b805d5c99c7931366c2e84e88f251.png";
                break;
        }

        return backgroundImage;
    }

}
