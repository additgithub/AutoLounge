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
import com.aprilinnovations.autoloungeindia.adapter.EmargencyContactList;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.EmargencyContactModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.EmergencyContactsResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmargencyContact extends AppCompatActivity {

    RecyclerView rv_emargencyContact;
    public EmargencyContactList emargencyContactList;
    List<EmargencyContactModel> emargencyContactModel = new ArrayList<>();
    Context myContext;
    UniversalHelper helper;
    private String userId, authToken, userCarDataId;
    ProgressDialog progressDialog;
    ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emargency_contact);


        myContext = this;
        helper = new UniversalHelper(this);

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        rv_emargencyContact = (RecyclerView)findViewById(R.id.rv_emargencyContact);

        emargencyContactList = new EmargencyContactList(emargencyContactModel, myContext);

        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (helper.isConnectingToInternet()){
            getEmargencyContactList();
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_emargencyContact.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(emargencyContactList);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_emargencyContact.setAdapter(slide);
    }


    private void getEmargencyContactList(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<EmergencyContactsResponse> call = getResponse.getEmergencyContactList(userId, authToken);
        call.enqueue(new Callback<EmergencyContactsResponse>() {
            @Override
            public void onResponse(Call<EmergencyContactsResponse> call, Response<EmergencyContactsResponse> response) {
                EmergencyContactsResponse emergencyContactsResponse = response.body();

                String contactName,contactNumber;


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (emergencyContactsResponse != null) {
                    Log.v("Responseabc", emergencyContactsResponse.getMessage());
                    if (TextUtils.equals(emergencyContactsResponse.getStatus(), "SUCCESS")){



                        if (emergencyContactsResponse.getData().getEmergencyDataList() != null){
                            List jsonArray = new ArrayList();
                            jsonArray = emergencyContactsResponse.getData().getEmergencyDataList();

                            for (int k = 0; k < jsonArray.size(); k++){

                                EmargencyContactModel emargencyContactModell = new EmargencyContactModel();

                                contactName = emergencyContactsResponse.getData().getEmergencyDataList().get(k).getContactName();
                                contactNumber = emergencyContactsResponse.getData().getEmergencyDataList().get(k).getPhoneNumber();


                                emargencyContactModell.setContactNiumber(contactNumber);
                                emargencyContactModell.setContactName(contactName);


                                emargencyContactModel.add(emargencyContactModell);
                                emargencyContactList.notifyDataSetChanged();
                            }
                        }

                    }
                } else {
                    assert emergencyContactsResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<EmergencyContactsResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
