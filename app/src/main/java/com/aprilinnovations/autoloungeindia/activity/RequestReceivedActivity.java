package com.aprilinnovations.autoloungeindia.activity;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.additinfotech.autoloungein.R;


public class RequestReceivedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_received);


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(RequestReceivedActivity.this, MainActivity.class));


            }
        }, secondsDelayed * 3000);


    }

    @Override
    public void onBackPressed() {

    }
}
