package edu.wmich.cs.johnharvey.weatherhw4_johnharvey;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private static final String TAG = "WeatherTempMain";

    private List<WeatherItem> mItems = new ArrayList<>();

    private String mNotificationTemp;

    private String validZip;

    private class FetchTemperature extends AsyncTask<Void, Void, List<WeatherItem>> {
        @Override
        protected List<WeatherItem> doInBackground(Void... params){
            /*
            try{
                String result = new TempFetcher().getUrlString("http://api.wunderground.com/api/5e9133a75e5ec9da/conditions/q/CA/San_Francisco.json");
                Log.i(TAG, "Fetched contents of URL " + result);
            }catch(IOException ioe) {
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            */

            return new TempFetcher(getZip()).fetchItems();
        }
        @Override
        protected void onPostExecute(List<WeatherItem> items){

            WeatherItem newItem;
            newItem = items.get(0);
            String temp = newItem.getmTemp();
            String city = newItem.getmCity();
            String iconUrl= newItem.getmIcon();

            mNotificationTemp = newItem.getmTemp();

            mItems = items;
            TextView tempTextView = (TextView)findViewById(R.id.tempTextView);
            TextView cityTextView = (TextView)findViewById(R.id.cityTextView);
            //ImageView iconImageView = (ImageView)findViewById(R.id.weatherImage);

            tempTextView.setText(temp);
            cityTextView.setText(city);

            //Drawable drawable = LoadImageFromWebOperations(iconUrl);

            //iconImageView.setImageDrawable(drawable);




        }
    }

/*************ON GET WEATHER BUTTON CLICK*****************/
    public void fetch(View v){
        try {

            try {
                if(!checkValidation(getZip())){
                    Toast.makeText(MainActivity.this, "Zip Code Error", Toast.LENGTH_LONG).show();
                }
                else{
                    new FetchTemperature().execute();
                    scheduleNotification(getNotification(mNotificationTemp), 60000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getZip(){

        EditText editText = (EditText)findViewById(R.id.editText);

        String zip = editText.getText().toString();

        return zip;
    }

    /******NOTIFICATION*******/

    private void scheduleNotification(Notification notification, int delay){

        /*
        ComponentName receiver = new ComponentName(this, RebootReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
                */

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        long alarmStart = calendar.get(Calendar.MILLISECOND) + 60000;
        long alarmFinish = 60000;

        long futureInMillis = SystemClock.elapsedRealtime() + delay;

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, alarmStart, alarmFinish, pendingIntent);

        //RebootReceiver rebootReceiver = new RebootReceiver();
        //rebootReceiver.scheduleAlarms(this, getNotification(mNotificationTemp));


        /*
        Intent rebootNotificationIntent = new Intent(this, RebootReceiver.class);
        rebootNotificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        rebootNotificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent rebootPendingIntent = PendingIntent.getBroadcast(this, 0, rebootNotificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    */


    }

    private Notification getNotification(String content){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle("Temperature Update");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_stat_name);


        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));


        return builder.build();
    }


    /******VALIDATION*******/

    private boolean checkValidation(String zip){
        boolean ret = true;

        if(!Validation.hasText(zip))
            ret = false;

        if(!Validation.isZipCode(zip, true))
            ret = false;

        return ret;
    }

/*
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    */


}
