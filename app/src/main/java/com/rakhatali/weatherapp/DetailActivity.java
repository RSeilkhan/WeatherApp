package com.rakhatali.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import static com.rakhatali.weatherapp.MainActivity.weatherList;

public class DetailActivity extends AppCompatActivity {
    TextView city, description, temperature, feelsLike, added_time;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        image = findViewById(R.id.weather_image);
        city = findViewById(R.id.city_value);
        description = findViewById(R.id.description_value);
        temperature = findViewById(R.id.temperature_value);
        feelsLike = findViewById(R.id.feel_like_value);
        added_time = findViewById(R.id.time_value);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Bundle arguments = getIntent().getExtras();
        int position = arguments.getInt("position");
        String icon = arguments.getString("icon");

        city.setText(weatherList.get(position).getCity());
        description.setText(weatherList.get(position).getDescription());
        temperature.setText(String.valueOf(weatherList.get(position).getTemperature()));
        feelsLike.setText(String.valueOf(weatherList.get(position).getFeelsLike()));
        added_time.setText(formatter.format(weatherList.get(position).getAdded_time()));

        Glide
                .with(getApplicationContext())
                .load(icon)
                .centerCrop()
                .into(image);
        }
}