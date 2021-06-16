package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.Model;
import com.aprilinnovations.autoloungeindia.adapter.ModelAdapter;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.ModelListResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectModelActivity extends AppCompatActivity {

    RecyclerView rv_model;
    public ModelAdapter modelAdapter;
    List<Model> modelList = new ArrayList<>();
    Context myContext;
    private String authToken;
    private String userId, modelName, carBrand;
    UniversalHelper helper;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_model);

        myContext = this;
        helper = new UniversalHelper(this);

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        rv_model = (RecyclerView)findViewById(R.id.rv_model);
        modelAdapter = new ModelAdapter(modelList, myContext);
        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");


        carBrand = helper.loadPreferences("carBrand");


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_model.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(modelAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_model.setAdapter(slide);

        if (helper.isConnectingToInternet()){
            carModelsApi();
        }

        //prepareData();
    }

    private void prepareData() {

        Model model = new Model("MARUTI SUZUKI 800");
        modelList.add(model);

        model = new Model("SWIFT");
        modelList.add(model);

        model = new Model("SWIFT DZIRE");
        modelList.add(model);

        model = new Model("ERTIGA");
        modelList.add(model);

        model = new Model("ALTO");
        modelList.add(model);
    }

    private void carModelsApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<ModelListResponse> call = getResponse.getModelList(userId, authToken, carBrand);
        call.enqueue(new Callback<ModelListResponse>() {
            @Override
            public void onResponse(Call<ModelListResponse> call, Response<ModelListResponse> response) {
                ModelListResponse serverResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        List jsonArray = new ArrayList();
                        jsonArray = serverResponse.getData().getCarModelDetails();

                        if (serverResponse.getData().getCarModelDetails() != null){

                            for (int k = 0; k < jsonArray.size(); k++){

                                Model model = new Model();
                                modelName = serverResponse.getData().getCarModelDetails().get(k);
                                model.setModel(modelName);
                                modelList.add(model);
                                modelAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelListResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
