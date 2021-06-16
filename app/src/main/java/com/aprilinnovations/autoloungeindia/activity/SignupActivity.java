package com.aprilinnovations.autoloungeindia.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.async.bean.PickResult;
import com.aprilinnovations.autoloungeindia.async.bundle.PickSetup;
import com.aprilinnovations.autoloungeindia.async.dialog.PickImageDialog;
import com.aprilinnovations.autoloungeindia.async.listeners.IPickResult;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.classes.RetrofitBodyParams;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserProfile;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mvc.imagepicker.ImagePicker;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IPickResult {

    public static int APP_REQUEST_CODE = 99;
    private static final String TAG = "SignupActivity";
    public String FirstTimeOtp = "2";
    private String authToken;
    public Button btn_next,btn_Update;
    private String clientId;
    private String emailId;
    public EditText et_email;
    public EditText et_name;
    UniversalHelper helper;
    public CircleImageView iv_signup;
    Context myContext;
    String originalFile;
    private String phoneNumber;
    private String profileUrl;
    public TextView tv_mobilenumber;
    private String userId;
    private String userName;
    ProgressDialog progressDialog;


    private static final int CROP_PIC_REQUEST_CODE = 100;

    String imagepath="";
    String fireBaseToken = "";
    String profilePicPicked = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initializeAll();
        helper = new UniversalHelper(this);
        myContext = this;

        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        helper.savePreferences("FirstTimeOtp", "1");
        userId = helper.loadPreferences("userId");
        authToken = helper.loadPreferences("authToken");
        profileUrl = helper.loadPreferences("profileImage");
        phoneNumber = helper.loadPreferences("phoneNumber");

        Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Thin.ttf");
        Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Medium.ttf");

        /*if (TextUtils.isEmpty(profileUrl)) {
            try {
                Glide.with(myContext).load(profileUrl).thumbnail(0.5f).into(iv_signup);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        tv_mobilenumber.setText(helper.loadPreferences("phoneNumber"));

        iv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(SignupActivity.this);
                //ImagePicker.pickImage(SignupActivity.this);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = et_name.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    et_name.setError("Cannot be empty.");
                    return;
                }
                emailId = et_email.getText().toString();
                if (TextUtils.isEmpty(emailId)) {
                    et_email.setError("Cannot be empty.");
                    return;
                }else {
                    if (helper.isConnectingToInternet()){
                        if (imagepath.equals("")){
                            helper.AlertDialogcall("Please upload profile picture.","","Ok",false,true);
                        }else {
                            singupApi();
                        }

                    }
                }

            }
        });


        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = et_name.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    et_name.setError("Cannot be empty.");
                    return;
                }
                emailId = et_email.getText().toString();
                if (TextUtils.isEmpty(emailId)) {
                    et_email.setError("Cannot be empty.");
                    return;
                }else {
                    if (helper.isConnectingToInternet()){
                        updateProfileApi();
                    }
                }

            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( SignupActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

                fireBaseToken = newToken;
            }
        });

        if (helper.loadPreferences("fromWhere").equals("mainActivity")){
            et_email.setFocusable(false);
            et_name.setFocusable(false);
            btn_Update.setVisibility(View.VISIBLE);
            btn_next.setVisibility(View.GONE);
            getProfileApi();
        }else {
            btn_Update.setVisibility(View.GONE);
            btn_next.setVisibility(View.VISIBLE);
        }





    }



    private void initializeAll() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        tv_mobilenumber = (TextView) findViewById(R.id.tv_mobilenumber);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_Update = (Button) findViewById(R.id.btn_Update);
        iv_signup = (CircleImageView) findViewById(R.id.iv_signup);
    }

    public void onPickImage(View view) {
        ImagePicker.pickImage((Activity) this, "Select your image:");
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
        if (requestCode != 0) {
            ImagePicker.setMinQuality(MediaPlayer2.MEDIA_INFO_VIDEO_TRACK_LAGGING, 600);
            iv_signup.setImageBitmap(bitmap);
            originalFile = helper.convertBase64String(bitmap);
            helper.savePreferences("original_file",originalFile);
        }
        if (bitmap != null) {
            iv_signup.setImageBitmap(bitmap);
        }
    }*/



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {


                Uri resultUri = result.getUri();

                iv_signup.setImageURI(resultUri);



            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    public void onPickResult(PickResult r) {
        imagepath = r.getPath();
        helper.savePreferences("userImage",imagepath);
        Uri imageuri = r.getUri();

        Log.d("imagepath==>",imagepath);
        Log.d("imageuri==>", String.valueOf(imageuri));

        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(SignupActivity.this);
        /*.start(PersonalInformation.this);*/

        //doCrop(imageuri);
        helper.savePreferences("profileImage",String.valueOf(imagepath));

    }

    private void doCrop(Uri picUri) {
        try {


            Intent cropIntent = new Intent("com.android.camera_photo.action.CROP");

            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, CROP_PIC_REQUEST_CODE);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {

            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void singupApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        RetrofitBodyParams retrofitBodyParams = new RetrofitBodyParams();
        retrofitBodyParams.profileImage = helper.loadPreferences("userImage");
        //retrofitBodyParams.profileImage = String.valueOf(resultUri);

        File file = new File(helper.loadPreferences("userImage"));

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profileImage", file.getName(), requestBody);

        Call<UserProfile> call = getResponse.updateProfile(fileToUpload, userName,emailId, userId, authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile serverResponse = response.body();

                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        String userId = serverResponse.getData().getUserDetails().getUserId();
                        String authToken = serverResponse.getData().getUserDetails().getAuthToken();
                        String profilePicture = serverResponse.getData().getUserDetails().getProfileImage();
                        String clientId = serverResponse.getData().getUserDetails().getClientId();
                        String userName = serverResponse.getData().getUserDetails().getUserName();
                        String emailId = serverResponse.getData().getUserDetails().getEmailId();
                        helper.savePreferences("userId", userId);
                        helper.savePreferences("authToken", authToken);
                        helper.savePreferences("profileImage", profilePicture);
                        helper.savePreferences("clientId", clientId);
                        helper.savePreferences("userName", userName);
                        helper.savePreferences("emailId", emailId);

                        startActivity(new Intent(SignupActivity.this, SelectCarActivity.class));
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    private void updateProfileApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        RetrofitBodyParams retrofitBodyParams = new RetrofitBodyParams();
        retrofitBodyParams.profileImage = UniversalHelper.loadPreferences("userImage");
        //retrofitBodyParams.profileImage = String.valueOf(resultUri);

        File file = new File(UniversalHelper.loadPreferences("userImage"));

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("profileImage", file.getName(), requestBody);

        Call<UserProfile> call = getResponse.updateProfile(fileToUpload, userName,emailId, userId, authToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile serverResponse = response.body();

                Log.v(" serverRes sta() >>", serverResponse.getStatus());
                Log.v(" serverRespo() data >>", serverResponse.getData().getUserDetails().toString());
                Log.v(" serverRespo() Id>>", serverResponse.getData().getUserDetails().getUserId());


                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                Log.v("Responseabc", serverResponse.getMessage());
                if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                    String userId = serverResponse.getData().getUserDetails().getUserId();
                    String authToken = serverResponse.getData().getUserDetails().getAuthToken();
                    String profilePicture = serverResponse.getData().getUserDetails().getProfileImage();
                    String clientId = serverResponse.getData().getUserDetails().getClientId();
                    String userName = serverResponse.getData().getUserDetails().getUserName();
                    String emailId = serverResponse.getData().getUserDetails().getEmailId();
                    UniversalHelper.savePreferences("userId", userId);
                    UniversalHelper.savePreferences("authToken", authToken);
                    UniversalHelper.savePreferences("profileImage", profilePicture);
                    UniversalHelper.savePreferences("clientId", clientId);
                    UniversalHelper.savePreferences("userName", userName);
                    UniversalHelper.savePreferences("emailId", emailId);

                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    private void getProfileApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Call<UserProfile> call = getResponse.getProfile(userId, authToken,fireBaseToken);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile serverResponse = response.body();

                Log.v("getProfileApi","called");
                Log.v(" call.request", call.request().toString());
                Log.v(" call.response", response.message());
                Log.v("code==>", String.valueOf(response.code()));
                if (serverResponse != null) {
                    Log.v("Responseabc", serverResponse.getMessage());
                    if (TextUtils.equals(serverResponse.getStatus(), "SUCCESS")){

                        //tv_mobilenumber.setText(serverResponse.getData().getUserDetails().);
                        et_name.setText(serverResponse.getData().getUserDetails().getUserName());
                        et_email.setText(serverResponse.getData().getUserDetails().getEmailId());

                        Glide.with(myContext).load(serverResponse.getData().getUserDetails().getProfileImage()).into(iv_signup);
                        helper.clearPreferences("fromWhere");

                        //startActivity(new Intent(SignupActivity.this, SelectCarActivity.class));
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
