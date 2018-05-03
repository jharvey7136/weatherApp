package edu.wmich.cs.johnharvey.weatherhw4_johnharvey;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by John on 6/20/2017.
 */

public class ServiceClass extends IntentService {


    public ServiceClass(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String intentType = intent.getExtras().getString("caller");
        if(intentType == null)
            return;

        if(intentType.equals("RebootReceiver"))
        {

        }

    }


}
