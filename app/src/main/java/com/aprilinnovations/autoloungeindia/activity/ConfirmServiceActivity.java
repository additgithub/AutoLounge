package com.aprilinnovations.autoloungeindia.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandInsertResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.LoginResponse;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmServiceActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;
    private static final String TAG = "ConfirmServiceActivity";
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String authToken;
    Button btn_address;
    Button btn_book;
    UniversalHelper helper;
    LinearLayout ll_address;
    LinearLayout ll_pickupdate,ll_pickupRequired;
    Context myContext;
    String pickUpLocation;
    String pickUpRequired = "NO";
    String pickupDate;
    Switch pickup_switch;
    String serviceName;
    String serviceType;
    TextView tv_add;
    //TextView tv_address;
    TextView tv_details;
    TextView tv_name;
    TextView tv_pickdate;
    TextView tv_pickupdate;
    TextView tv_pickupreq;
    TextView tv_service;
    TextView tv_servicename;
    TextView tv_servicetype;
    String userCarDataId;
    private String userId;
    ProgressDialog progressDialog;
    final Calendar myCalendar = Calendar.getInstance();
    String picUpDate = "";
    PlacesClient placesClient;
    private Task<FindCurrentPlaceResponse> currentPlaceTask;
    String userAddress = "";
    ImageButton btn_back;
    //String apiKey = "AIzaSyARI_a3h744vZDhyRaouYqkjsK7O2JZcV8";
    String apiKey = "AIzaSyDaE8mWnhAeGefIzAjTi-rSKTJUy0r783g";
    //AIzaSyARI_a3h744vZDhyRaouYqkjsK7O2JZcV8


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_service);

        initializeAll();
        pickUpRequired = "NO";
        pickupDate = null;
        //tv_pickupdate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        helper = new UniversalHelper(this);
        myContext = this;

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");
        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        userCarDataId = UniversalHelper.loadPreferences("userCarDataId");
        serviceName = getIntent().getExtras().getString("serviceName");
        serviceType = getIntent().getExtras().getString("serviceType");


        String address = "";

        if (TextUtils.isEmpty(serviceName)) {

        }else {
            tv_servicename.setText(serviceName);
        }
        if (TextUtils.isEmpty(serviceType)) {

        }else {
            tv_servicetype.setText(serviceType);
        }
        Typeface bundle = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");
        tv_servicetype.setTypeface(bundle);
        tv_servicename.setTypeface(bundle);
        //tv_address.setTypeface(bundle);
        tv_add.setTypeface(createFromAsset);
        tv_details.setTypeface(createFromAsset);
        tv_pickupdate.setTypeface(bundle);
        tv_pickdate.setTypeface(createFromAsset);
        tv_pickupreq.setTypeface(createFromAsset);
        tv_service.setTypeface(createFromAsset);
        tv_name.setTypeface(createFromAsset);
        btn_book.setTypeface(createFromAsset);
        btn_address.setTypeface(createFromAsset);


        pickup_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (pickup_switch.isChecked()){
                    pickUpRequired = "YES";
                    ll_pickupdate.setVisibility(View.VISIBLE);
                }else {
                    ll_pickupdate.setVisibility(View.GONE);
                    pickUpRequired = "NO";
                }
            }

        });



        btn_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_address.setVisibility(View.VISIBLE);
                ll_pickupRequired.setVisibility(View.VISIBLE);

                btn_book.setVisibility(View.VISIBLE);
            }
        });



        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final DatePickerDialog.OnDateSetListener dateForward = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };


        ll_pickupdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(myContext, R.style.TintTheme,dateForward, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        if(!Places.isInitialized()){
            Places.initialize(getApplicationContext(),apiKey);

        }else {
            Places.initialize(getApplicationContext(),apiKey);

        }

        placesClient = Places.createClient(myContext);

        final AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autoComplete_fragment);

        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,Place.Field.NAME));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                userAddress = place.getName();

            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });

    }

    public void validation(){
        if (userAddress.isEmpty()){
            helper.AlertDialogcall("Please enter address.","","Ok",false,true);
        }else if (pickUpRequired.equals("YES")){
            if (picUpDate.isEmpty()){
                helper.AlertDialogcall("Please enter date.","","Ok",false,true);
            }else {
                if (helper.isConnectingToInternet()){
                    bookService();
                }
            }
        } else {
            if (helper.isConnectingToInternet()){
                bookService();
            }
        }
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Date date = null;
        try {
            date = sdf.parse(sdf.format(Calendar.getInstance().getTime()));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if ( sdf.parse(sdf.format(myCalendar.getTime())).before(date)) {
                helper.AlertDialogcall("You can not select past date.","","Ok",false,true);
            }else {
                tv_pickupdate.setText(sdf.format(myCalendar.getTime()));
                picUpDate = tv_pickupdate.getText().toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    private void initializeAll() {
        tv_servicetype = (TextView) findViewById(R.id.tv_servicetype);
        tv_servicename = (TextView) findViewById(R.id.tv_servicename);
        //tv_address = (TextView) findViewById(R.id.tv_address);
        btn_address = (Button) findViewById(R.id.btn_address);
        btn_book = (Button) findViewById(R.id.btn_book);
        ll_address = (LinearLayout) findViewById(R.id.ll_address);
        ll_pickupdate = (LinearLayout) findViewById(R.id.ll_pickupdate);
        ll_pickupRequired = (LinearLayout) findViewById(R.id.ll_pickupRequired);
        pickup_switch = (Switch) findViewById(R.id.pickup_switch);
        tv_pickupdate = (TextView) findViewById(R.id.tv_pickupdate);
        tv_details = (TextView) findViewById(R.id.tv_details);
        tv_service = (TextView) findViewById(R.id.tv_service);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_pickupreq = (TextView) findViewById(R.id.tv_pickupreq);
        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_pickdate = (TextView) findViewById(R.id.tv_pickdate);
        btn_back =  findViewById(R.id.btn_back);
    }


    private void bookService(){

        progressDialog.show();


        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);
        Call<BrandInsertResponse> call = null;

        if (pickUpRequired.equals("YES")) {
            call = getResponse.addUserService(userId, authToken, userCarDataId, serviceType, serviceName, userAddress, pickUpRequired, picUpDate);
        }else if(pickUpRequired.equals("NO")) {
            call = getResponse.addUserServiceNoPickUp(userId, authToken, userCarDataId, serviceType, serviceName, userAddress, pickUpRequired);
        }



        call.enqueue(new Callback<BrandInsertResponse>() {
            @Override
            public void onResponse(Call<BrandInsertResponse> call, Response<BrandInsertResponse> response) {

                BrandInsertResponse serverResponse = response.body();

                String serviceDataId, serviceType, serviceName ;


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));

                if (serverResponse != null) {

                    Log.v("Responseabc", serverResponse.getMessage());

                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){
                        Intent intent = new Intent(ConfirmServiceActivity.this,RequestReceivedActivity.class);
                        startActivity(intent);
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BrandInsertResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }
}
