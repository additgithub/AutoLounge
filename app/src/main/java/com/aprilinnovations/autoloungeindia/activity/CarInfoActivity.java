package com.aprilinnovations.autoloungeindia.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;

import android.net.NetworkRequest;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandInsertResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.LoginResponse;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarInfoActivity extends AppCompatActivity {

    public static int APP_REQUEST_CODE = 99;
    private static final String TAG = "CarInfoActivity";

    private String authToken;
    private Button btn_addcar;
    private Button btn_delete;
    private Button btn_update;
    String carDataId;
    String dateOfPurchase;
    String editInfo;
    EditText et_dop;
    private EditText et_kmrun;
    private EditText et_regno;
    UniversalHelper helper;
    String kmRun;
    private LinearLayout ll_updateInfo;
    Context myContext;
    String registrationNo;
    TextView tv_carinfo;
    String userCarDataId;
    private String userId;
    ProgressDialog progressDialog;
    final Calendar myCalendar = Calendar.getInstance();
    ImageButton btn_back;
    RelativeLayout custom_toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);


        initializeAll();
        myContext = this;
        helper = new UniversalHelper(this);

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");


            Bundle bundle = getIntent().getExtras();

            try {
                carDataId = bundle.getString("carDataId");
                userCarDataId = bundle.getString("userCarDataIdMain");
                dateOfPurchase = bundle.getString("dateOfPurchaseMain");
                kmRun = bundle.getString("kilometersMain");
                registrationNo = bundle.getString("carRegistrationNumberMain");
                editInfo = bundle.getString("editInfo");

            } catch (Exception e) {
                e.printStackTrace();
            }

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        if (TextUtils.equals(editInfo, "true")) {

        }
        if (TextUtils.isEmpty(dateOfPurchase)) {
            et_dop.setText(dateOfPurchase);
        }
        if (TextUtils.isEmpty(kmRun)) {
            et_kmrun.setText(kmRun);
        }
        if (TextUtils.isEmpty(registrationNo)) {
            et_regno.setText(registrationNo);
        }

        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");
        tv_carinfo.setTypeface(createFromAsset);
        et_regno.setTypeface(createFromAsset);
        et_kmrun.setTypeface(createFromAsset);
        et_dop.setTypeface(createFromAsset);

        btn_addcar.setTypeface(createFromAsset);
        btn_delete.setTypeface(createFromAsset);
        btn_update.setTypeface(createFromAsset);

        if (helper.loadPreferences("comeFrom").equals("mainActivity")){

            btn_addcar.setVisibility(View.GONE);
            ll_updateInfo.setVisibility(View.VISIBLE);
            custom_toolbar.setVisibility(View.VISIBLE);

            et_dop.setFocusable(false);
            String s= dateOfPurchase;
            String[] parts = s.split("T"); // escape .
            String part1 = parts[0];

            et_regno.setText(registrationNo);
            et_kmrun.setText(kmRun);
            et_dop.setText(part1);
        }

        btn_addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaidation();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.isConnectingToInternet()){
                    AlertDialogcall("Are you sure? You want to delete car?","Yes","No",true,true);

                }
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaidation();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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



        et_dop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (helper.loadPreferences("comeFrom").equals("mainActivity")){

                }else {
                    // TODO Auto-generated method stub
                    new DatePickerDialog(myContext, dateForward, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

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
            if ( sdf.parse(sdf.format(myCalendar.getTime())).after(date)) {
               helper.AlertDialogcall("You can not select future date.","","Ok",false,true);
            }else {
                et_dop.setText(sdf.format(myCalendar.getTime()));
                dateOfPurchase = et_dop.getText().toString();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void initializeAll() {
        et_regno = findViewById(R.id.et_regno);
        et_kmrun = findViewById(R.id.et_kmrun);
        et_dop = findViewById(R.id.et_dop);
        btn_addcar = findViewById(R.id.btn_addcar);
        ll_updateInfo = findViewById(R.id.ll_updateInfo);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        tv_carinfo = findViewById(R.id.tv_carinfo);
        btn_back = findViewById(R.id.btn_back);
        custom_toolbar = findViewById(R.id.custom_toolbar);
    }

    public void vaidation(){
        String resistrationNo = et_regno.getText().toString();
        String kmRun = et_kmrun.getText().toString();
        String dateOfPurchase = et_dop.getText().toString();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            Date dop = dateFormat.parse(dateOfPurchase);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (resistrationNo.isEmpty()){
            et_regno.setError("Please enter registration number");
        }
        else if (kmRun.isEmpty()){
            et_kmrun.setError("Please enter Kilometers");
        }
        else if (dateOfPurchase.isEmpty()){
            et_dop.setError("Please enter date of purchase");

        }
        else {
            if (helper.loadPreferences("comeFrom").equals("mainActivity")){
                if (helper.isConnectingToInternet()) {
                    updateCarInfo();
                }
            }
            if (helper.loadPreferences("comeFrom").equals("varientActivity")){
                if (helper.isConnectingToInternet()) {
                    addCarApi();
                }
            }

        }

    }

    private void addCarApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        registrationNo = et_regno.getText().toString();
        dateOfPurchase = et_dop.getText().toString();
        kmRun = et_kmrun.getText().toString();

        Call<BrandInsertResponse> call = getResponse.addNewCar(userId, authToken, carDataId, registrationNo, dateOfPurchase, kmRun);
        Log.v(" call.request", call.request().toString());

        call.enqueue(new Callback<BrandInsertResponse>() {
            @Override
            public void onResponse(Call<BrandInsertResponse> call, Response<BrandInsertResponse> response) {
                BrandInsertResponse serverResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        startActivity(new Intent(CarInfoActivity.this, MainActivity.class));
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

    private void updateCarInfo(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        registrationNo = et_regno.getText().toString();
        dateOfPurchase = et_dop.getText().toString();
        kmRun = et_kmrun.getText().toString();



        Call<BrandInsertResponse> call = getResponse.updateCarInfo(userId, authToken, userCarDataId, kmRun, dateOfPurchase, registrationNo);
        call.enqueue(new Callback<BrandInsertResponse>() {
            @Override
            public void onResponse(Call<BrandInsertResponse> call, Response<BrandInsertResponse> response) {
                BrandInsertResponse loginResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (loginResponse != null) {
                    Log.v("Responseabc", loginResponse.getMessage());
                    if (TextUtils.equals(loginResponse.getStatus(), "SUCCESS")){

                        custom_toolbar.setVisibility(View.GONE);
                        startActivity(new Intent(CarInfoActivity.this, MainActivity.class));
                    }
                } else {
                    assert loginResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BrandInsertResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    private void deleteCar(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        registrationNo = et_regno.getText().toString();
        dateOfPurchase = et_dop.getText().toString();
        kmRun = et_kmrun.getText().toString();



        Call<BrandInsertResponse> call = getResponse.deleteUserCar(userId, authToken, userCarDataId);
        call.enqueue(new Callback<BrandInsertResponse>() {
            @Override
            public void onResponse(Call<BrandInsertResponse> call, Response<BrandInsertResponse> response) {
                BrandInsertResponse loginResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (loginResponse != null) {
                    Log.v("Responseabc", loginResponse.getMessage());
                    if (TextUtils.equals(loginResponse.getStatus(), "SUCCESS")){

                        startActivity(new Intent(CarInfoActivity.this, MainActivity.class));
                    }
                } else {
                    assert loginResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<BrandInsertResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }


    public void  AlertDialogcall(String title,String positiveButton,String negativeButton, boolean setPossitiveButton,boolean setNegativeButton) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext, R.style.TintTheme);

        builder.setTitle(title);

        if (setPossitiveButton == true){
            builder.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (helper.isConnectingToInternet()) {
                        deleteCar();
                    }
                }
            });
        }


        if(setNegativeButton == true){
            builder.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }





        builder.show();
    }
}
