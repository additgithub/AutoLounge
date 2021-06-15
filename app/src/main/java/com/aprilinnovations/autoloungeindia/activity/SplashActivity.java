package com.aprilinnovations.autoloungeindia.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;

public class SplashActivity extends PermisoActivity {

    UniversalHelper helper;
    String loggedIn = "";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        helper = new UniversalHelper(this);
        context = this;
        loggedIn = helper.loadPreferences("loggedIn");
        helper.clearPreferences("isLocationChanged");


        final int secondsDelayed = 1;
        /*new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));


            }
        }, secondsDelayed * 2000);*/
        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
             @Override
             public void onPermissionResult(final Permiso.ResultSet resultSet) {
                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {


                         if (resultSet.areAllPermissionsGranted()) {

                             if(helper.loadPreferences("userIsLogin").equals("Yes")){
                                 startActivity(new Intent(SplashActivity.this, MainActivity.class));
                             }else if (helper.loadPreferences("userIsLogin").equals("No")){

                                 startActivity(new Intent(SplashActivity.this, GetOtpActivity.class));
                             }else {
                                 startActivity(new Intent(SplashActivity.this, GetOtpActivity.class));
                             }


                         } else {
                             if(helper.loadPreferences("userIsLogin").equals("Yes")){
                                 startActivity(new Intent(SplashActivity.this, MainActivity.class));
                             }else if (helper.loadPreferences("userIsLogin").equals("No")){

                                 startActivity(new Intent(SplashActivity.this, GetOtpActivity.class));
                             }else {
                                 startActivity(new Intent(SplashActivity.this, GetOtpActivity.class));
                             }
                         }
                     }
                 },secondsDelayed * 2000);

             }

             @Override
             public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                 Permiso.getInstance().showRationaleInDialog("Title", "Message", null, callback);
             } } , Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.INTERNET
                , Manifest.permission.CHANGE_NETWORK_STATE
                , Manifest.permission.CAMERA
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_WIFI_STATE);

    }
}
