package com.aprilinnovations.autoloungeindia.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aprilinnovations.autoloungeindia.R;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.LoginResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetOtpActivity extends AppCompatActivity {

    private static final String TAG = "GetOtpActivity";

    private static int TIME_OUT = 450;

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ProgressDialog mDialog;

    private EditText mPhoneNumberField;
    private EditText mVerificationField;
    private RelativeLayout mStartButton;
    private RelativeLayout mVerifyButton;
    private RelativeLayout mResendButton;
    LinearLayout rl_enterMobile, ll_otp;
    TextView tv_didnt, tv_notyour, tv_otpsent, tv_proceed,tv_resend, tv_submit ;
    String fireBaseToken = "";

    // private Button mSignOutButton;
    UniversalHelper helper;

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";


    Context myContext;
    ProgressDialog progressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);

        myContext = this;
        helper = new UniversalHelper(this);
        helper.loadPreferences("ProfileFirstTime",myContext);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(myContext);
        progressDialog.setMessage("Please wait...");

        initializeAll();


        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue Light.ttf");
        //tv_enternumber.setTypeface(createFromAsset);
        tv_proceed.setTypeface(createFromAsset);
        tv_otpsent.setTypeface(createFromAsset);
        mPhoneNumberField.setTypeface(createFromAsset);
        mVerificationField.setTypeface(createFromAsset);
        tv_notyour.setTypeface(createFromAsset);
        tv_submit.setTypeface(createFromAsset);
        tv_didnt.setTypeface(createFromAsset);
        tv_resend.setTypeface(createFromAsset);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( GetOtpActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);

                fireBaseToken = newToken;
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.

                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    //mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    /*Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();*/
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                //storing the verification id that is sent to the user
                mVerificationId = s;
            }
        };



        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = mPhoneNumberField.getText().toString();
                if (!TextUtils.isEmpty(phoneNumber)){
                    if (phoneNumber.length() != 10){
                        mPhoneNumberField.setError("Mobile Number must be 10 Digits");
                    }
                    if (phoneNumber.equals("")){
                        mPhoneNumberField.setError("Please enter phone number");
                    }else {
                        mVerifyButton.setVisibility(View.VISIBLE);
                        rl_enterMobile.setVisibility(View.GONE);
                        ll_otp.setVisibility(View.VISIBLE);
                        sendVerificationCode(phoneNumber);
                        helper.savePreferences("phoneNumber",phoneNumber);
                        //loginApi();
                        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                        if (!validatePhoneNumber()) {
                            return;
                        }

                        tv_otpsent.setText("We have sent an OTP to "+ helper.loadPreferences("phoneNumber"));

                        if (helper.isConnectingToInternet()){
                            startPhoneNumberVerification(phoneNumber);
                        }
                    }
                }else {
                    //mPhoneNumberField.setError("This Field cannot be empty.");
                }
            }
        });

        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (helper.isConnectingToInternet()){
                    verifyPhoneNumberWithCode( mVerificationField.getText().toString());
                }
            }
        });

        mResendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (helper.isConnectingToInternet()){
                    resendVerificationCode(helper.loadPreferences("phoneNumber"), mResendToken);
                }

                new AlertDialog.Builder(myContext, R.style.TintTheme)
                        .setMessage("Your new OTP has been sent.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        tv_notyour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mVerifyButton.setVisibility(View.GONE);
                rl_enterMobile.setVisibility(View.VISIBLE);
                ll_otp.setVisibility(View.GONE);
                enableViews(mStartButton, mPhoneNumberField);


            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // [START_EXCLUDE]
        /*if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(mPhoneNumberField.getText().toString());
        }*/
        // [END_EXCLUDE]
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {


        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);         // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String code) {

        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);


    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                "+91"+mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            loginApi();
                            if (helper.isConnectingToInternet()){
                               // startActivity(new Intent(GetOtpActivity.this, SignupActivity.class));

                            }


                            helper.savePreferences("FirstTimeOtp","1");
                            String phoneNumber = helper.loadPreferences("phoneNumber");
                            helper.savePreferences("phoneNumber", phoneNumber);
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {

                            helper.savePreferences("FirstTimeOtp", "0");
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                enableViews(mStartButton, mPhoneNumberField);
                disableViews(mVerifyButton, mResendButton, mVerificationField);
                //mDetailText.setText(null);
                break;
            case STATE_CODE_SENT:
                // Code sent state, show the verification field, the
                enableViews(mVerifyButton, mResendButton, mPhoneNumberField, mVerificationField);
                disableViews(mStartButton);
                //mDetailText.setText(R.string.status_code_sent);
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,mVerificationField);
                //mDetailText.setText(R.string.status_verification_failed);
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,mVerificationField);
                //mDetailText.setText(R.string.status_verification_succeeded);

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        String verifyCode = cred.getSmsCode();
                        mVerificationField.setText(verifyCode);

                    } else {
                        //mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                //mDetailText.setText(R.string.status_sign_in_failed);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            // Signed out
            //mPhoneNumberViews.setVisibility(View.VISIBLE);
            //mSignedInViews.setVisibility(View.GONE);

            //mStatusText.setText(R.string.signed_out);;
        } else {
            // Signed in
            //mPhoneNumberViews.setVisibility(View.GONE);
            //mSignedInViews.setVisibility(View.VISIBLE);

            enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            mVerificationField.setText(null);

            /*mStatusText.setText(R.string.signed_in);
            mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));*/
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = "+91"+mPhoneNumberField.getText().toString();

        helper.savePreferences("phoneNumber", phoneNumber);
        if (TextUtils.isEmpty(phoneNumber)) {
            //mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    private void initializeAll() {
        mStartButton = (RelativeLayout) findViewById(R.id.btn_proceed);
        tv_otpsent = (TextView) findViewById(R.id.tv_otpsent);
        tv_notyour = (TextView) findViewById(R.id.tv_notyour);
        mVerifyButton = (RelativeLayout) findViewById(R.id.btn_submit);
        mPhoneNumberField = (EditText) findViewById(R.id.et_mobile);
        mVerificationField = (EditText) findViewById(R.id.et_otp);
        mResendButton = (RelativeLayout) findViewById(R.id.btn_resend);
        rl_enterMobile = (LinearLayout) findViewById(R.id.rl_enterMobile);
        ll_otp = (LinearLayout) findViewById(R.id.ll_otp);
        //tv_enternumber = (TextView) findViewById(R.id.tv_enternumber);
        tv_proceed = (TextView) findViewById(R.id.tv_proceed);
        tv_didnt = (TextView) findViewById(R.id.tv_didnt);
        tv_submit = (TextView) findViewById(R.id.tv_submit);
        tv_resend = (TextView) findViewById(R.id.tv_resend);
    }

    private void loginApi(){

        progressDialog.show();

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        Log.d("mobileNo",helper.loadPreferences("phoneNumber"));
        Call<LoginResponse> call = getResponse.userLogin("ANDROID", helper.loadPreferences("phoneNumber"),fireBaseToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse serverResponse = response.body();

                Log.v("iam","in Login");
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
                        helper.savePreferences("userId", userId);
                        helper.savePreferences("authToken", authToken);
                        helper.savePreferences("profileImage", profilePicture);
                        helper.savePreferences("clientId", clientId);

                        //startActivity(new Intent(GetOtpActivity.this, SignupActivity.class));

                        if (TextUtils.equals(String.valueOf(response.code()), "200")){
                            startActivity(new Intent(GetOtpActivity.this, MainActivity.class));
                        }else if (TextUtils.equals(String.valueOf(response.code()), "201")){
                            helper.savePreferences("comeFrom","getOtpActivity");
                            startActivity(new Intent(GetOtpActivity.this, SignupActivity.class));
                        }
                    }
                } else {
                    assert serverResponse != null;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
