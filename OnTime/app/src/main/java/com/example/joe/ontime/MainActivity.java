package com.example.joe.ontime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.AlarmManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity{

    //Alarm Manager
    private static final int SECOND_ACTIVITY_RESULT_CODE = 0;
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    String address;
    PendingIntent pending_intent;
    boolean went_sec = false; // Checks if we went to second page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;

        // initialize our AlarmManager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // initialize our timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker5);

        //initialize text_update box
        update_text = (TextView) findViewById(R.id.update_text);

        //create an instance of Calendar
        final Calendar calendar = Calendar.getInstance();


        //Create an intent to the Alarm Receiver
        final Intent myIntent = new Intent(this.context, Alarm_Receiver.class);

        //Link to new page
        Button new_page = (Button) findViewById(R.id.work_dest);

        new_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SecondActivity
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivityForResult(intent, SECOND_ACTIVITY_RESULT_CODE);
            }
        });

        //initialize start buttons
        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        // Create an OnClick listener to start the alarm

        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(23) public void onClick(View v) {

                // Setting Calendar with the hour and minutes that we picked on the TimePicker
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                //get int of hour and minutes
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                if(hour > 12){
                    hour = hour % 12;
                }


                //Convert int to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if(minute < 10){
                    minute_string = "0" + String.valueOf(minute);
                }


                // method that changes update text text-box
                set_alarm_text("Alarm set to: "+ hour_string + ":" + minute_string );

                //Insert extra string into myIntent
                //Tell the clock you pressed the "alarm on" button

                myIntent.putExtra("extra",true);

                // create pending intent that delays intent to specified time
                pending_intent = PendingIntent.getBroadcast(MainActivity. this, 0, myIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                // set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);
            }
        });

        //initialize stop buttons
        Button alarm_off = (Button) findViewById(R.id.alarm_off);

        //Create an OnClick listener to stop the alarm or undo an alarm set.

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(23) public void onClick(View v) {

                // method that changes update text text-box
                set_alarm_text("Alarm Off");
                // cancel the alarm
                alarm_manager.cancel(pending_intent);

                //put extra string into my intent
                //tell the clock you pressed the off button
                myIntent.putExtra("extra",false);

                // Stop the ringtone
                sendBroadcast(myIntent);
            }
        });



    }

    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_RESULT_CODE) {
            if (resultCode == 2) {

                // get String data from Intent
                String returnString = data.getExtras().getString("some_key");
                Log.e("works",returnString);
                // set text view with string
                //TextView textView = (TextView) findViewById(R.id.textView);
                //textView.setText(returnString);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
