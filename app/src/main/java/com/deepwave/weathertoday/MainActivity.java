package com.deepwave.weathertoday;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {


    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    Button seeMore;

    WeatherTask weatherTask = new WeatherTask();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new WeatherTask().execute();

        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        temp_minTxt = findViewById(R.id.temp_min);
        temp_maxTxt = findViewById(R.id.temp_max);
        sunriseTxt = findViewById(R.id.sunrise);
        sunsetTxt = findViewById(R.id.sunset);
        windTxt = findViewById(R.id.wind);
        pressureTxt = findViewById(R.id.pressure);
        humidityTxt = findViewById(R.id.humidity);
        seeMore = findViewById(R.id.seeMore);
        seeMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent openFeeds = new Intent(MainActivity.this, ChooseCountry.class);
                startActivity(openFeeds);
            }
        });


        try{
        /* Populating extracted data into our views */
        addressTxt.setText(weatherTask.address);
        updated_atTxt.setText(weatherTask.updatedAtText);
        statusTxt.setText(weatherTask.weatherDescription.toUpperCase());
        tempTxt.setText(weatherTask.temp);
        temp_minTxt.setText(weatherTask.tempMin);
        temp_maxTxt.setText(weatherTask.tempMax);
        sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(weatherTask.sunrise * 1000)));
        sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(weatherTask.sunset * 1000)));
        windTxt.setText(weatherTask.windSpeed);
        pressureTxt.setText(weatherTask.pressure);
        humidityTxt.setText(weatherTask.humidity);

        /* Views populated, Hiding the loader, Showing the main design */
        findViewById(R.id.loader).setVisibility(View.GONE);
        findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);

        if(weatherTask.loader && weatherTask.errorText){
            findViewById(R.id.loader).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.VISIBLE);
        }
        }
        catch (NullPointerException e){
            statusTxt.setText("N/A");
        }
    }

}
