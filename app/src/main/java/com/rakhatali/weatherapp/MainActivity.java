package com.rakhatali.weatherapp;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements WeatherAdapter.OnWeatherListener{

    private RecyclerView recyclerView;
    private WeatherAdapter weatherAdapter;
    public static List<Weather> weatherList;
    private FloatingActionButton add_btn;
    private static final String API_LINK =
            "http://api.openweathermap.org/data/2.5/weather?q=Almaty&APPID=32ed9f902544e06c9b138544d9cd7862&units=metric";
    public static final String SHARED_PREFS = "sharedPref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_btn = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        weatherList = new ArrayList<>();
        weatherAdapter = new WeatherAdapter(this, weatherList, this);
        recyclerView.setAdapter(weatherAdapter);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieveJson();
            }
        });
    }

    private void retrieveJson() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                API_LINK, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObj = (JSONObject) response.getJSONArray("weather").get(0);
                    JSONObject main = response.getJSONObject("main");

                    Date date = new Date(System.currentTimeMillis());

                    weatherList.add(0, new Weather (response.getString("name"), jsonObj.getString("description"),
                            main.getDouble("temp"), main.getDouble("feels_like"), date));
                    weatherAdapter.notifyItemInserted(0);
                    saveShared("weatherData");

                } catch (JSONException e) {
                    Log.e("JSON", "Some fields not presented. ERROR:" + e);
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("JSON", "Error: " + error.getMessage());
            }
        });

        RequestQueueController.getInstance(this).addToRequestQueue(jsonObjReq);
    }


    public void saveShared(String key){
        SharedPreferences mPrefs = getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(weatherList);
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, json);
        editor.commit();
    }

    @Override
    public void onWeatherClick(int position, ImageView image, String icon) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("icon", icon);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(MainActivity.this, (View)image, getString(R.string.image_transition));
        startActivity(intent, options.toBundle());
    }
}