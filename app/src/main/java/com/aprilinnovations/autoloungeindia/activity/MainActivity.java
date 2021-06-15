package com.aprilinnovations.autoloungeindia.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.adapter.CarAdapter;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.Car;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.LoginResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserCarListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserProfile;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rv_car;
    public CarAdapter carAdapter;
    List<Car> carList = new ArrayList<>();
    Context myContext;
    ImageView iv_bell;
    UniversalHelper helper;
    private String userId, authToken;
    ProgressDialog progressDialog;
    TextView tv_name,tv_april;
    ImageView iv_profilepic,iv_facebook,iv_insta;
    LinearLayout ll_addNewCar,ll_logOut,ll_emargencyContact,ll_aboutus,ll_contactUs,ll_profile;
    String fireBaseToken = "";
    String newToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myContext = this;

        helper = new UniversalHelper(this);

        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        rv_car = (RecyclerView)findViewById(R.id.rv_cars);

        iv_bell = (ImageView)findViewById(R.id.iv_bell);

        tv_name = findViewById(R.id.tv_name);
        tv_april = findViewById(R.id.tv_april);
        iv_profilepic = findViewById(R.id.iv_profilepic);
        iv_facebook = findViewById(R.id.iv_facebook);
        iv_insta = findViewById(R.id.iv_insta);

        ll_addNewCar = findViewById(R.id.ll_addNewCar);
        ll_logOut = findViewById(R.id.ll_logOut);
        ll_emargencyContact = findViewById(R.id.ll_emargencyContact);
        ll_aboutus = findViewById(R.id.ll_aboutus);
        ll_contactUs = findViewById(R.id.ll_contactUs);
        ll_profile = findViewById(R.id.ll_profile);

        carAdapter = new CarAdapter(carList, myContext);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        //RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        /*The above line is to show items horizontally*/
        rv_car.setLayoutManager(mLayoutManager);

        SlideInLeftAnimationAdapter slide = new SlideInLeftAnimationAdapter(carAdapter);
        slide.setDuration(500);
        slide.setStartPosition(0);
        rv_car.setAdapter(slide);

        helper.savePreferences("userIsLogin","Yes");



        ll_addNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.savePreferences("comeFrom","getOtpActivity");
                startActivity(new Intent(MainActivity.this, SelectCarActivity.class));
            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.savePreferences("fromWhere","mainActivity");
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });

        ll_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogcall("Are you sure? You want to logout?","Yes","No",true,true);

            }
        });

        ll_emargencyContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmargencyContact.class));
            }
        });



        iv_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://m.facebook.com/The-Auto-lounge-india-294717891317875/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ll_contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "9820004449"));
                startActivity(intent);

            }
        });

        ll_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://theautoloungeindia.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        iv_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("https://www.instagram.com/theautoloungeindia/");

                Intent i= new Intent(Intent.ACTION_VIEW,uri);

                i.setPackage("com.instagram.android");

                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {

                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/theautoloungeindia/")));
                }

            }
        });

        tv_april.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://aprilinnovations.com/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //prepareData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        iv_bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(i);
            }
        });



        if (helper.isConnectingToInternet()){
            userCarListApi();
            getUserPrifile();
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userCarListApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<UserCarListResponse> call = getResponse.getUserCarData(userId, authToken);
        call.enqueue(new Callback<UserCarListResponse>() {
            @Override
            public void onResponse(Call<UserCarListResponse> call, Response<UserCarListResponse> response) {
                UserCarListResponse serverResponse = response.body();

                String userCarDataId, carBrand, carImage, carModel, carRegistrationNumber, dateOfPurchase, kilometers;


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        List jsonArray = new ArrayList();
                        jsonArray = serverResponse.getData().getUserCarDetails();

                        if (serverResponse.getData().getUserCarDetails() != null){

                            for (int k = 0; k < jsonArray.size(); k++){

                                Car car = new Car();
                                userCarDataId = serverResponse.getData().getUserCarDetails().get(k).getUserCarDataId();
                                carBrand = serverResponse.getData().getUserCarDetails().get(k).getCarBrand();
                                carImage = serverResponse.getData().getUserCarDetails().get(k).getCarImage();
                                carImage = serverResponse.getData().getUserCarDetails().get(k).getCarImage();
                                carModel = serverResponse.getData().getUserCarDetails().get(k).getCarModel();
                                carRegistrationNumber = serverResponse.getData().getUserCarDetails().get(k).getCarRegistrationNumber();
                                dateOfPurchase = serverResponse.getData().getUserCarDetails().get(k).getDateOfPurchase();
                                kilometers = serverResponse.getData().getUserCarDetails().get(k).getKilometers();

                                car.setUserCarDataId(userCarDataId);
                                car.setCarBrand(carBrand);
                                car.setCarModel(carModel);
                                car.setCarImageUrl(serverResponse.getData().getUserCarDetails().get(k).getCarImage());
                                car.setCarNumber(carRegistrationNumber);
                                car.setDateOfPurchase(dateOfPurchase);
                                car.setKmRun(kilometers);
                                carList.add(car);
                                carAdapter.notifyDataSetChanged();
                            }
                        }

                        //startActivity(new Intent(CarInfoActivity.this, MainActivity.class));
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserCarListResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    private void getUserPrifile(){

        progressDialog.show();

        final ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MainActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newTokenMain",newToken);

                fireBaseToken = newToken;


                Call<UserProfile> call = getResponse.getProfile(userId, authToken,newToken);
                call.enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        UserProfile userProfile = response.body();

                        Log.v(" call.request", call.request().toString());
                        Log.v(" call.response", response.message());
                        Log.v("code==>", String.valueOf(response.code()));
                        if (userProfile != null) {
                            Log.v("Responseabc", userProfile.getMessage());
                            if (TextUtils.equals(userProfile.getStatus(), "SUCCESS")){

                                tv_name.setText(userProfile.getData().getUserDetails().getUserName());

                                Glide.with(myContext).load(userProfile.getData().getUserDetails().getProfileImage()).into(iv_profilepic);

                                //startActivity(new Intent(CarInfoActivity.this, MainActivity.class));
                            }
                        } else {
                            assert userProfile != null;
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        Log.v("Responset", t.toString());

                    }
                });
            }
        });



    }

    private void logOut(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<LoginResponse> call = getResponse.userLogout(userId, authToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (loginResponse != null) {
                    Log.v("Responseabc", loginResponse.getMessage());
                    if (TextUtils.equals(loginResponse.getStatus(), "SUCCESS")){

                        helper.savePreferences("userIsLogin","No");
                        startActivity(new Intent(MainActivity.this, GetOtpActivity.class));
                    }
                } else {
                    assert loginResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
                        logOut();
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
