package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.NotificationDataResponse;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationDetails extends AppCompatActivity {
    public static int APP_REQUEST_CODE = 99;
    private static final String TAG = "NotificationDetails";

    private String authToken;
    ImageButton btn_back;
    private String compressedImage;
    private String createTime;
    Uri datafromlinkdata;
    private String description;
    UniversalHelper helper;
    public ImageView iv_notificationdetails;
    Context myContext;
    private String notificationDataId;
    private String title;
    public TextView tv_date;
    public TextView tv_description;
    public TextView tv_title;
    private String userId;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notification_details);
        initializeAll();

        myContext = this;
        helper = new UniversalHelper(this);
        notificationDataId = getIntent().getStringExtra("notificationDataId");
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        tv_title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf"));
        tv_date.setTypeface(createFromAsset);
        tv_title.setTypeface(createFromAsset);

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        if (helper.isConnectingToInternet()) {
            checkLinkData();
        }
    }

    private void initializeAll() {
        this.iv_notificationdetails = (ImageView) findViewById(R.id.iv_notificationdetails);
        this.tv_title = (TextView) findViewById(R.id.tv_title);
        this.tv_date = (TextView) findViewById(R.id.tv_date);
        this.tv_description = (TextView) findViewById(R.id.tv_description);
        this.btn_back = (ImageButton) findViewById(R.id.btn_back);
    }


    public void checkLinkData() {
        if (getIntent() != null) {

            Bundle bundle = getIntent().getExtras();

            notificationDataId = bundle.getString("notificationID");

                if (helper.isConnectingToInternet()) {
                    getNotificationDetails(this.notificationDataId);
                }
            }

    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    private void getNotificationDetails(String notificationDataId){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<NotificationDataResponse> call = getResponse.getOneNotification(userId, authToken, notificationDataId);
        call.enqueue(new Callback<NotificationDataResponse>() {
            @Override
            public void onResponse(Call<NotificationDataResponse> call, Response<NotificationDataResponse> response) {
                NotificationDataResponse notificationDataResponse = response.body();

                String date,image,description,title;


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (notificationDataResponse != null) {
                    Log.v("Responseabc", notificationDataResponse.getMessage());
                    if (TextUtils.equals(notificationDataResponse.getStatus(), "SUCCESS")){



                        tv_title.setText(notificationDataResponse.getData().getNotificationData().getTitle());
                        tv_date.setText(notificationDataResponse.getData().getNotificationData().getCreateTime());
                        tv_description.setText(notificationDataResponse.getData().getNotificationData().getDescription());
                        image = notificationDataResponse.getData().getNotificationData().getCompressedImage();
                        if (!TextUtils.isEmpty(image)) {
                            Glide.with(myContext).load(notificationDataResponse.getData().getNotificationData().getCompressedImage()).into(iv_notificationdetails);
                        }
                    }
                } else {
                    assert notificationDataResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotificationDataResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
