package edu.wmich.cs.johnharvey.weatherhw4_johnharvey;

import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by John on 6/18/2017.
 */

public class TempFetcher  {

    private static final String TAG = "TempFetcher";
    private static final String API_KEY = "5e9133a75e5ec9da";

    public String zipInput;

    public TempFetcher(String input){
        zipInput = input;
    }



    public byte[] getUrlBytes(String urlSpec) throws IOException{

        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();

        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<WeatherItem> fetchItems() {
        List<WeatherItem> items = new ArrayList<>();

        try{
            String url = Uri.parse("http://api.wunderground.com/api/").buildUpon()
                    .appendPath(API_KEY)
                    .appendPath("conditions")
                    .appendPath("q")
                    .appendPath(zipInput)
                    .appendPath(".json").build().toString();

            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);

        } catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je){
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }

    private void parseItems(List<WeatherItem> items, JSONObject jsonBody) throws IOException, JSONException   {

        JSONObject mTempJsonObject = jsonBody.getJSONObject("current_observation");

        JSONObject mCityJsonObject = jsonBody.getJSONObject("current_observation").getJSONObject("display_location");



        String temp = mTempJsonObject.getString("temperature_string");
        String city = mCityJsonObject.getString("full");
        String icon = mTempJsonObject.getString("icon_url");

        WeatherItem item = new WeatherItem();

        item.setmTemp(temp);
        item.setmCity(city);
        item.setmIcon(icon);

        //JSONArray cityJsonObject = jsonBody.getJSONArray("display_location");


        //item.setmCity(cityJsonObject.getString("full"));

        items.add(item);

    }







}
