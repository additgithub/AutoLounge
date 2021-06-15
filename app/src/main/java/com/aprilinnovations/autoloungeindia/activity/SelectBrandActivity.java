package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.aprilinnovations.autoloungeindia.adapter.BrandAdapter;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.BrandModel;
import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandListResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectBrandActivity extends AppCompatActivity {

    RecyclerView rv_brand;
    public BrandAdapter brandAdapter;
    List<BrandModel> brandModelList = new ArrayList<>();
    Context myContext;
    ProgressDialog progressDialog;
    UniversalHelper helper;

    private String authToken;
    private String userId, brandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_brand);

        myContext = this;
        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");
        helper = new UniversalHelper(this);
        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");


        rv_brand = (RecyclerView)findViewById(R.id.rv_brand);

        brandAdapter = new BrandAdapter(brandModelList, myContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_brand.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(brandAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_brand.setAdapter(slide);

        //prepareData();

        if (helper.isConnectingToInternet()){
            carBrandsApi();
        }
    }


    private void carBrandsApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<BrandListResponse> call = getResponse.getBrandList(userId, authToken);
        call.enqueue(new Callback<BrandListResponse>() {
            @Override
            public void onResponse(Call<BrandListResponse> call, Response<BrandListResponse> response) {
                BrandListResponse serverResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        List jsonArray = new ArrayList();
                        jsonArray = serverResponse.getData().getCarBrandDetails();

                        if (serverResponse.getData().getCarBrandDetails() != null){

                            for (int k = 0; k < jsonArray.size(); k++){

                                BrandModel brandModel = new BrandModel();
                                brandName = serverResponse.getData().getCarBrandDetails().get(k);
                                brandModel.setBrandName(brandName);
                                brandModelList.add(brandModel);
                                brandAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BrandListResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }


}
