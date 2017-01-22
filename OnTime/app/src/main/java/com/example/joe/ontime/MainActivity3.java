package com.example.joe.ontime;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity3 extends AppCompatActivity {
    String new_info;
    Button enter_info;
    EditText address;
    Boolean sent = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Go back to Alarm clock page
        Button return_home = (Button) findViewById(R.id.return_home);
        return_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Take value from text fields
        enter_info = (Button) findViewById(R.id.enter_info);
        address  = (EditText)findViewById(R.id.Address);

        enter_info.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        new_info = address.getText().toString();
                        Log.e("works",new_info);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("some_key", new_info);
                        setResult(2, resultIntent);
                        finish();
                    }
                });

    }

}
