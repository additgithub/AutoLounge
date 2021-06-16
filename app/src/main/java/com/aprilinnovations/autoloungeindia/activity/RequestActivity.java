package com.aprilinnovations.autoloungeindia.activity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;

public class RequestActivity extends AppCompatActivity {

    RelativeLayout rl_repair, rl_servicing;
    UniversalHelper helper;
    Context myContext;
    String userCarDataId = "";
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        helper = new UniversalHelper(this);
        myContext = this;
        userCarDataId = getIntent().getExtras().getString("userCarDataId");
        helper.savePreferences("userCarDataId", userCarDataId);

        rl_repair = findViewById(R.id.rl_repair);
        rl_servicing = findViewById(R.id.rl_servicing);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rl_repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(RequestActivity.this, RepairActivity.class));
            }
        });

        rl_servicing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(RequestActivity.this, ServiceTypeActivity.class));
            }
        });

    }
}
