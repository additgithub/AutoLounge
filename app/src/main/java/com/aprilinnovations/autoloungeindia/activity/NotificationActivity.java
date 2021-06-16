package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.additinfotech.autoloungein.R;

import com.aprilinnovations.autoloungeindia.adapter.NotificationAdapter;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.NotificationDataModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.NotificationListResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView rv_notification;
    public NotificationAdapter notificationAdapter;
    List<NotificationDataModel> notificationDataModelList = new ArrayList<>();
    Context myContext;
    UniversalHelper helper;
    private String userId, authToken, userCarDataId;
    ProgressDialog progressDialog;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        myContext = this;
        helper = new UniversalHelper(this);

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        rv_notification = (RecyclerView)findViewById(R.id.rv_notification);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        notificationAdapter = new NotificationAdapter(notificationDataModelList, myContext);

        if (helper.isConnectingToInternet()){
            getNotification();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_notification.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(notificationAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_notification.setAdapter(slide);

       // prepareData();
    }


    private void getNotification(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<NotificationListResponse> call = getResponse.getNotificationList(userId, authToken, "0");
        call.enqueue(new Callback<NotificationListResponse>() {
            @Override
            public void onResponse(Call<NotificationListResponse> call, Response<NotificationListResponse> response) {
                NotificationListResponse notificationListResponse = response.body();

                String date,image,description,title,notificationId;


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (notificationListResponse != null) {
                    Log.v("Responseabc", notificationListResponse.getMessage());
                    if (TextUtils.equals(notificationListResponse.getStatus(), "SUCCESS")){



                        if (notificationListResponse.getData().getNotificationDetails() != null){
                            List jsonArray = new ArrayList();
                            jsonArray = notificationListResponse.getData().getNotificationDetails();

                            for (int k = 0; k < jsonArray.size(); k++){

                                NotificationDataModel notificationDataModel = new NotificationDataModel();

                                date = notificationListResponse.getData().getNotificationDetails().get(k).getCreateTime();
                                image = notificationListResponse.getData().getNotificationDetails().get(k).getCompressedImage();
                                description = notificationListResponse.getData().getNotificationDetails().get(k).getDescription();
                                title =  notificationListResponse.getData().getNotificationDetails().get(k).getTitle();
                                notificationId = notificationListResponse.getData().getNotificationDetails().get(k).getNotificationDataId();


                                notificationDataModel.setDate(date);
                                notificationDataModel.setNotificationImage(notificationListResponse.getData().getNotificationDetails().get(k).getCompressedImage());
                                notificationDataModel.setDescription(description);
                                notificationDataModel.setHeading(title);
                                notificationDataModel.setNotificationDataId(notificationId);


                                notificationDataModelList.add(notificationDataModel);
                                notificationAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                } else {
                    assert notificationListResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NotificationListResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
