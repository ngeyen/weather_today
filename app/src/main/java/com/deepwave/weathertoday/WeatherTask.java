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

import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class WeatherTask extends AsyncTask<String, Void, String>
{
    String updatedAtText, temp, tempMin, tempMax, pressure, humidity, windSpeed, weatherDescription, address;
    Long sunrise, sunset, updatedAt;
    boolean loader, errorText;
    String CITY = "Kigali, RW";
    String API = "5068173e2b315f66fd6a0944ae995dd1";
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /* Showing the ProgressBar, Making the main design GONE */


    }
    protected String doInBackground(String... params) {

        String str="https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API;
        URLConnection urlConn = null;
        BufferedReader bufferedReader = null;
        try
        {
            URL url = new URL(str);
            urlConn = url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            return stringBuffer.toString();
        }
        catch(Exception ex)
        {
            Log.e("App", "yourDataTask", ex);
            return null;
        }
        finally
        {
            if(bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

            address = sys.getString("name") + ", " + sys.getString("country");


        } catch (JSONException e) {
            errorText = true;
            loader = true;
        }

    }
}