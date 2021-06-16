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
import com.aprilinnovations.autoloungeindia.adapter.ServiceAdapter;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.ServiceModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.ServiceNameListResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceTypeActivity extends AppCompatActivity {

    RecyclerView rv_service;
    public ServiceAdapter serviceAdapter;
    List<ServiceModel> serviceModels = new ArrayList<>();
    Context myContext;
    UniversalHelper helper;
    ProgressDialog progressDialog;
    private String authToken;
    private String userId;
    ImageButton btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_type);

        myContext = this;

        helper = new UniversalHelper(this);
        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");
        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        rv_service = (RecyclerView)findViewById(R.id.rv_service);
        btn_back = findViewById(R.id.btn_back);

        serviceAdapter = new ServiceAdapter(serviceModels, myContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_service.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(serviceAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_service.setAdapter(slide);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //prepareData();

        if (helper.isConnectingToInternet()){
            serviceListApi();
        }
    }



    private void serviceListApi(){

        progressDialog.show();


        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<ServiceNameListResponse> call = getResponse.getServiceNameList(userId, authToken, "Servicing");
        call.enqueue(new Callback<ServiceNameListResponse>() {
            @Override
            public void onResponse(Call<ServiceNameListResponse> call, Response<ServiceNameListResponse> response) {

                ServiceNameListResponse serverResponse = response.body();

                String serviceDataId, serviceType, serviceName ;


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));

                if (serverResponse != null) {

                    Log.v("Responseabc", serverResponse.getMessage());

                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        List jsonArray = new ArrayList();
                        jsonArray = serverResponse.getData().getServiceNameDetails();

                        if (serverResponse.getData().getServiceNameDetails() != null){

                            for (int k = 0; k < jsonArray.size(); k++){

                                ServiceModel model = new ServiceModel();

                                serviceDataId = serverResponse.getData().getServiceNameDetails().get(k).getServiceDataId();
                                serviceType = serverResponse.getData().getServiceNameDetails().get(k).getServiceType();
                                serviceName = serverResponse.getData().getServiceNameDetails().get(k).getServiceName();

                                model.setServiceDataId(serviceDataId);
                                model.setServiceType(serviceType);
                                model.setServiceName(serviceName);

                                serviceModels.add(model);
                                serviceAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ServiceNameListResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
