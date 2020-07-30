package com.deepwave.weathertoday;


import android.os.AsyncTask;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class WeatherTask extends AsyncTask<String, Void, String> {


    String updatedAtText, temp, tempMin, tempMax, pressure, humidity, windSpeed, weatherDescription, address;
    Long sunrise, sunset, updatedAt;
    boolean loader, errorText;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /* Showing the ProgressBar, Making the main design GONE */


    }
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
                Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {


        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject main = jsonObj.getJSONObject("main");
            JSONObject sys = jsonObj.getJSONObject("sys");
            JSONObject wind = jsonObj.getJSONObject("wind");
            JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

            updatedAt = jsonObj.getLong("dt");
            updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
            temp = main.getString("temp") + "°C";
            tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
            tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
            pressure = main.getString("pressure");
            humidity = main.getString("humidity");

            sunrise = sys.getLong("sunrise");
            sunset = sys.getLong("sunset");
            windSpeed = wind.getString("speed");
            weatherDescription = weather.getString("description");

            address = jsonObj.getString("name") + ", " + sys.getString("country");




        } catch (JSONException e) {
            errorText = true;
            loader = true;
        }

    }
}