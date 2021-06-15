package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.adapter.VariantAdapter;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.VariantModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.VarientListResponse;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectVarientActivity extends AppCompatActivity {

    RecyclerView rv_variant;
    public VariantAdapter variantAdapter;
    List<VariantModel> variantModelList = new ArrayList<>();
    Context myContext;
    private String authToken;
    private String userId, carBrand, carModel;
    UniversalHelper helper;
    ProgressDialog progressDialog;
    public ImageView iv_car;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_varient);

        myContext = this;
        helper = new UniversalHelper(this);

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        iv_car = (ImageView) findViewById(R.id.iv_car);


        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");
        carBrand = helper.loadPreferences("carBrand");
        carModel = helper.loadPreferences("carModel");



        rv_variant = (RecyclerView)findViewById(R.id.rv_variant);
        variantAdapter = new VariantAdapter(variantModelList, myContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_variant.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(variantAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_variant.setAdapter(slide);

        //prepareData();
        if (helper.isConnectingToInternet()){
            carVarientApi();
        }
    }



    private void carVarientApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<VarientListResponse> call = getResponse.getVarientList(userId, authToken, carBrand, carModel);
        call.enqueue(new Callback<VarientListResponse>() {
            @Override
            public void onResponse(Call<VarientListResponse> call, Response<VarientListResponse> response) {

                VarientListResponse serverResponse = response.body();

                String modelName, brandName, carDataId, carVariant, carImage;

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());

                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        List jsonArray = new ArrayList();
                        jsonArray = serverResponse.getData().getCarVariantDetails();

                        if (serverResponse.getData().getCarVariantDetails() != null){

                            carImage = serverResponse.getData().getCarVariantDetails().get(0).getCarImage();
                            Glide.with(myContext).load(carImage).thumbnail(0.5f).into(iv_car);

                            for (int k = 0; k < jsonArray.size(); k++){

                                VariantModel model = new VariantModel();
                                modelName = serverResponse.getData().getCarVariantDetails().get(k).getCarModel();
                                brandName = serverResponse.getData().getCarVariantDetails().get(k).getCarBrand();
                                carDataId = serverResponse.getData().getCarVariantDetails().get(k).getCarDataId();
                                carVariant = serverResponse.getData().getCarVariantDetails().get(k).getCarVariant();
                                model.setVariantName(carVariant);
                                model.setCarDataId(carDataId);
                                variantModelList.add(model);
                                variantAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<VarientListResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
