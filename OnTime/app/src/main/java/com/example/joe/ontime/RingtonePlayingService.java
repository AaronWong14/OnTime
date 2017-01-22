package com.example.joe.ontime;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Joe on 1/21/17.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;

    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra values.
        boolean states = intent.getExtras().getBoolean("extra");

        // convert extra string from intent
        if(states){
            startId = 1;
        }
        else if(!states){
            startId = 0;
        }
        else{
            startId = 0;
        }

        // if no music and user presses alarm on
        // music should start playing
        if(!this.isRunning && startId==1){
            Log.e("1 is running","running");
            media_song = MediaPlayer.create(this, R.raw.phone);
            media_song.start();
            this.isRunning = true;
            startId = 0;
        }

        // if there is music playing and user presses alarm off
        // music should stop playing
        else if(this.isRunning && startId==0){
            Log.e("2 is running","running");
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            startId = 0;
        }

        // if user presses random buttons
        // just to bug proof the app
        // if there is no music and user press alarm off, do nothing
        else if (!this.isRunning && startId==0) {
            this.isRunning = true;
            startId = 1;
        }

        // if there is music playing and user presses on
        // do nothing
        else if (this.isRunning && startId==1){
            this.isRunning = true;
            startId = 0;
        }
        // just to catch the odd events
        else{

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this,"On Destroy called", Toast.LENGTH_SHORT).show();
    }

}
