package com.example.joe.ontime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Joe on 1/21/17.
 */

public class Alarm_Receiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("We are in the receiver","Here");

        //fetch extra string from intent
        boolean get_boolean = intent.getExtras().getBoolean("extra");

        //print out
        Log.e("Check boolean",String.valueOf(get_boolean));
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        //pass string from activity to ringtoneplayingservice

        service_intent.putExtra("extra",get_boolean);

        //start the ringtone service
        context.startService(service_intent);

    }
}
