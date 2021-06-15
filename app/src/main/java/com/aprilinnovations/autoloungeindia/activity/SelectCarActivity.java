package com.aprilinnovations.autoloungeindia.activity;

import android.content.Intent;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aprilinnovations.autoloungeindia.R;

public class SelectCarActivity extends AppCompatActivity {

    Button btn_proceed2;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_select_car);

        Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Thin.ttf");
        Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");

        btn_proceed2 = (Button) findViewById(R.id.btn_proceed2);
        btn_proceed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectCarActivity.this, SelectBrandActivity.class));

            }
        });
    }
}
