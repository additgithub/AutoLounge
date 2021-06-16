package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.adapter.ServiceHistoryAdapter;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.ServiceHistoryModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserCarServiceInfoResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarDetailsActivity extends AppCompatActivity {

    RecyclerView rv_cardetails;
    public ServiceHistoryAdapter serviceHistoryAdapter;
    List<ServiceHistoryModel> serviceHistoryModels = new ArrayList<>();
    Context myContext;
    UniversalHelper helper;
    private String userId, authToken;
    ProgressDialog progressDialog;

    public static int APP_REQUEST_CODE = 99;
    private static final String TAG = "CarDetailsActivity";
    ImageButton btn_back;
    ImageView iv_car2;
    private LinearLayout ll_selected_car;
    private RelativeLayout rl_request;
    TextView tv_carname2;
    TextView tv_carnumber;
    TextView tv_km;
    TextView tv_request;
    TextView tv_totalrun;

    String userCarDataId, carBrand, carImage, carModel, carRegistrationNumber, dateOfPurchase, kilometers,
            serviceDataId, serviceName, serviceType, serviceStatus, createTime, userServiceChatId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        initializeAll();

        myContext = this;
        helper = new UniversalHelper(this);

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");


        Bundle bundle = getIntent().getExtras();
        userCarDataId = bundle.getString("userCarDataId");

        rv_cardetails = (RecyclerView)findViewById(R.id.rv_cardetails);

        serviceHistoryAdapter = new ServiceHistoryAdapter(serviceHistoryModels, myContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_cardetails.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(serviceHistoryAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_cardetails.setAdapter(slide);

        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");
        Typeface createFromAsset2 = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        tv_carname2.setTypeface(createFromAsset2);
        tv_carnumber.setTypeface(createFromAsset);
        tv_km.setTypeface(createFromAsset);
        tv_totalrun.setTypeface(createFromAsset);
        tv_request.setTypeface(createFromAsset);
        //prepareData();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (helper.isConnectingToInternet()){
            userCarServiceInfoApi();
        }

        rl_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CarDetailsActivity.this, RequestActivity.class);
                i.putExtra("userCarDataId", userCarDataId);
                startActivity(i);
                //startActivity(new Intent(CarDetailsActivity.this, RequestActivity.class));
            }
        });


        ll_selected_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarDetailsActivity.this,CarInfoActivity.class);
                intent.putExtra("userCarDataIdMain", userCarDataId);
                intent.putExtra("dateOfPurchaseMain", dateOfPurchase);
                intent.putExtra("kilometersMain", kilometers);
                intent.putExtra("carRegistrationNumberMain", carRegistrationNumber);
                helper.savePreferences("comeFrom","mainActivity");
                startActivity(intent);
            }
        });

    }

    private void userCarServiceInfoApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<UserCarServiceInfoResponse> call = getResponse.getUserCarServiceInfo(userId, authToken, userCarDataId);
        call.enqueue(new Callback<UserCarServiceInfoResponse>() {
            @Override
            public void onResponse(Call<UserCarServiceInfoResponse> call, Response<UserCarServiceInfoResponse> response) {
                UserCarServiceInfoResponse serverResponse = response.body();




                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        carImage = serverResponse.getData().getCarDetails().getCarImage();
                        kilometers = serverResponse.getData().getCarDetails().getKilometers();
                        carRegistrationNumber = serverResponse.getData().getCarDetails().getCarRegistrationNumber();
                        carBrand = serverResponse.getData().getCarDetails().getCarBrand();
                        carModel = serverResponse.getData().getCarDetails().getCarModel();
                        carModel = serverResponse.getData().getCarDetails().getCarModel();
                        dateOfPurchase = serverResponse.getData().getCarDetails().getDateOfPurchase();

                        tv_km.setText(kilometers);
                        tv_carnumber.setText(carRegistrationNumber);
                        tv_carname2.setText(carBrand+ " "+ carModel);

                        List jsonArray = new ArrayList();
                        jsonArray = serverResponse.getData().getServiceDetails();

                        if (serverResponse.getData().getServiceDetails() != null){

                            for (int k = 0; k < jsonArray.size(); k++){

                                ServiceHistoryModel serviceHistoryModel = new ServiceHistoryModel();

                                serviceDataId = serverResponse.getData().getServiceDetails().get(k).getUserServiceDataId();
                                serviceName = serverResponse.getData().getServiceDetails().get(k).getServiceName();
                                serviceType = serverResponse.getData().getServiceDetails().get(k).getServiceType();
                                serviceStatus = serverResponse.getData().getServiceDetails().get(k).getServiceStatus();
                                createTime = serverResponse.getData().getServiceDetails().get(k).getCreateTime();
                                userServiceChatId = serverResponse.getData().getServiceDetails().get(k).getUserServiceChatId();

                                serviceHistoryModel.setUserServiceDataId(serviceDataId);
                                serviceHistoryModel.setServiceName(serviceName);
                                serviceHistoryModel.setType(serviceType);
                                serviceHistoryModel.setDate(createTime);
                                serviceHistoryModel.setStatus(serviceStatus);
                                serviceHistoryModel.setUserServiceChatId(userServiceChatId);

                                serviceHistoryModels.add(serviceHistoryModel);
                                serviceHistoryAdapter.notifyDataSetChanged();
                            }
                        }

                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserCarServiceInfoResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    private void initializeAll() {
        tv_carname2 = (TextView) findViewById(R.id.tv_carname2);
        tv_carnumber = (TextView) findViewById(R.id.tv_carnumber);
        tv_km = (TextView) findViewById(R.id.tv_km);
        tv_totalrun = (TextView) findViewById(R.id.tv_totalrun);
        iv_car2 = (ImageView) findViewById(R.id.iv_car2);
        ll_selected_car = (LinearLayout) findViewById(R.id.ll_selected_car);
        rl_request = (RelativeLayout) findViewById(R.id.rl_request);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        tv_request = (TextView) findViewById(R.id.tv_request);
    }


}
