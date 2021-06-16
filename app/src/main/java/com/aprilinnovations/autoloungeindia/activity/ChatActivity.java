package com.aprilinnovations.autoloungeindia.activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.additinfotech.autoloungein.R;
import com.aprilinnovations.autoloungeindia.adapter.ChatAdapter;
import com.aprilinnovations.autoloungeindia.async.bean.PickResult;
import com.aprilinnovations.autoloungeindia.async.bundle.PickSetup;
import com.aprilinnovations.autoloungeindia.async.dialog.PickImageDialog;
import com.aprilinnovations.autoloungeindia.async.listeners.IPickResult;
import com.aprilinnovations.autoloungeindia.classes.ApiConfig;
import com.aprilinnovations.autoloungeindia.classes.AppConfig;
import com.aprilinnovations.autoloungeindia.datamodel.ChatModel;
import com.aprilinnovations.autoloungeindia.helper.UniversalHelper;
import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandInsertResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.ChatResponce;
import com.aprilinnovations.autoloungeindia.retrofitResponse.GetChatResponce;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IPickResult {


    RecyclerView rv_chat;
    ChatAdapter ChatAdapter;
    List<ChatModel> ChatList = new ArrayList<>();
    Context myContext;
    EditText et_message;

    private UniversalHelper helper;
    Context context;
    ProgressDialog progressDialog;
    private int responseCode;
    String userType = "doctor";
    String email;
    String password;
    String deviceType="android";
    private String firstRelativeId;
    private String firstRelativeName;
    private String firstRelativeNumber;
    private String firstRelativeEmailId ;
    private String patientIdd ;

    private String secondRelativeId;
    private String secondRelativeName;
    private String secondRelativeNumber;
    private String secondRelativeEmailId ;
    private String firstRelativeRelation;
    private String secondtRelativeRelation;
    String chatDataId;
    String message;
    String prefrence;
    String time;
    String image ="";
    String messageToBeSend;
    String userServiceChatId;
    String chatMessage="";
    boolean hasImage = false;
    ImageView iv_chatImageFull;
    LinearLayout rl_msgtyping;
    ImageButton custom_toolbar,btn_backOne;
    RelativeLayout rl_back,rl_top,rl_selectedImage;
    ImageView iv_pickedimage,iv_cross;


    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1*1000;
    int chatLimit;
    RelativeLayout rl_sendMsg;

    RelativeLayout rl_addImg;
    private String imagepath="";
    private static final int CROP_PIC_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context = this;
        helper = new UniversalHelper(this);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait for movement...");

        myContext = this;

        if (helper.isConnectingToInternet()){
            getChat();
        }
        rl_sendMsg=findViewById(R.id.rl_sendMsg);
        et_message=findViewById(R.id.et_message);
        custom_toolbar=findViewById(R.id.custom_toolbar);
        btn_backOne=findViewById(R.id.btn_backOne);

        rl_addImg=findViewById(R.id.rl_addImg);
        iv_chatImageFull = findViewById(R.id.iv_chatImageFull);
        rl_msgtyping = findViewById(R.id.rl_msgtyping);
        rl_back = findViewById(R.id.rl_back);
        rl_top = findViewById(R.id.rl_top);
        rl_selectedImage = findViewById(R.id.rl_selectedImage);
        iv_pickedimage = findViewById(R.id.iv_pickedimage);
        iv_cross = findViewById(R.id.iv_cross);

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagepath = "";
                hasImage = false;
                UniversalHelper.clearPreferences("userImage");
                rl_selectedImage.setVisibility(View.GONE);
            }
        });

        custom_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iv_chatImageFull.setVisibility(View.GONE);
                rl_top.setVisibility(View.GONE);
                rv_chat.setVisibility(View.VISIBLE);
                rl_msgtyping.setVisibility(View.VISIBLE);
            }
        });

        rl_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup()).show(ChatActivity.this);
            }
        });

        btn_backOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();

            }
        });



        rl_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageToBeSend = et_message.getText().toString();
                if (hasImage){
                    sendMessageImage();
                }else {
                    authentication();
                }


            }
        });

        rv_chat= (RecyclerView)findViewById(R.id.rv_chat);


        ChatAdapter = new ChatAdapter(ChatList, myContext);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());/*for showing items vertically */
        mLayoutManager.setStackFromEnd(true);
        rv_chat.setLayoutManager(mLayoutManager);



        rv_chat.setAdapter(ChatAdapter);



        rv_chat.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /*@Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy == sampleList.size()){

                    Toast.makeText(myContext, "last object", Toast.LENGTH_SHORT).show();

                }
            }*/
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    public void onPickResult(PickResult r) {
        imagepath = r.getPath();
        rl_selectedImage.setVisibility(View.VISIBLE);
        iv_pickedimage.setVisibility(View.VISIBLE);
        Glide.with(context).load(imagepath).into(iv_pickedimage);
        UniversalHelper.savePreferences("userImage",imagepath);
        hasImage = true;

        Uri imageuri = r.getUri();



        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ChatActivity.this);
        /*.start(PersonalInformation.this);*/

        //doCrop(imageuri);

        UniversalHelper.savePreferences("profileImage",String.valueOf(imagepath));

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
    protected void onResume() {
        //start handler as activity become visible

        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                if (helper.isConnectingToInternet()){
                    //getChat();
                }

                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable); //stop handler when activity not visible

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getChat() {
        progressDialog.show();

        String userId = UniversalHelper.loadPreferences("userId");
       String authToken = UniversalHelper.loadPreferences("authToken");



        Intent intent = getIntent();

        Bundle b =intent.getExtras();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String activity = extras.getString("activity");

            assert activity != null;
            if(activity.equalsIgnoreCase("serviceHistory")){

                userServiceChatId = extras.getString("userChatId");

            }
            else if(activity.equalsIgnoreCase("normalFlow")) {
                userServiceChatId = extras.getString("caseIdCaseList");

            }else {
                userServiceChatId = extras.getString("userChatId");
            }
        }


        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);


        Call<GetChatResponce> call = getResponse.getUserServiceChatData(userId,authToken,userServiceChatId);
        call.enqueue(new Callback<GetChatResponce>() {
            @Override
            public void onResponse(Call<GetChatResponce> call, Response<GetChatResponce> response) {
                GetChatResponce getChatResponce = response.body();
                responseCode = response.code();
                Log.v("==>call.request", call.request().toString());
                Log.v("==>call.response", response.message());
                Log.v("==>call.request", String.valueOf(call.request()));
                Log.v("==>requestcode", String.valueOf(response.code()));
                Log.v("==>call.responsebody", String.valueOf(response.body()));
                Log.v("Success==>", new Gson().toJson(response.body()));

                ChatList.clear();

                if (TextUtils.equals(String.valueOf(response.code()), "200")) {


                    if (getChatResponce.getData().getUserServiceChatDetails().size() == 0) {
                        Toast.makeText(ChatActivity.this, "No Chat Yet", Toast.LENGTH_SHORT).show();

                    }else {
                        for (int i = 0; i < getChatResponce.getData().getUserServiceChatDetails().size(); i++) {

                            chatDataId = getChatResponce.getData().getUserServiceChatDetails().get(i).getChatDataId();


                            message = getChatResponce.getData().getUserServiceChatDetails().get(i).getMessage();


                            prefrence = getChatResponce.getData().getUserServiceChatDetails().get(i).getPreference();


                            time = getChatResponce.getData().getUserServiceChatDetails().get(i).getCreateTime();

                            String hasImage = getChatResponce.getData().getUserServiceChatDetails().get(i).getHasImage();


                            image = getChatResponce.getData().getUserServiceChatDetails().get(i).getChatImageUrl();


                            ChatModel chatModel = new ChatModel(chatDataId, message, prefrence, time, image, hasImage);
                            ChatList.add(chatModel);

                            ChatAdapter.notifyDataSetChanged();

                            int noOfChatItem = getChatResponce.getData().getUserServiceChatDetails().size();

                            rv_chat.smoothScrollToPosition(noOfChatItem);

                        }
                    }


                }else {
                    Toast.makeText(ChatActivity.this, "No Chat Yet", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<GetChatResponce> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    private void sendMessageImage() {
        progressDialog.show();

        String userId = UniversalHelper.loadPreferences("userId");
       String authToken = UniversalHelper.loadPreferences("authToken");

       Intent intent = getIntent();

        Bundle b =intent.getExtras();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userServiceChatId = extras.getString("userChatId");

        }




            ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);

        File file = new File(helper.loadPreferences("userImage"));


        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("chatImage", file.getName(), requestBody);

            Call<BrandInsertResponse> call = getResponse.addMessageToUserServiceChatImage(fileToUpload,userId,authToken,messageToBeSend,userServiceChatId,hasImage);
            call.enqueue(new Callback<BrandInsertResponse>() {
                @Override
                public void onResponse(Call<BrandInsertResponse> call, Response<BrandInsertResponse> response) {
                    BrandInsertResponse chatResponce = response.body();
                    responseCode = response.code();
                    Log.v("==>call.requestSend", call.request().toString());
                    Log.v("==>call.responseSend", response.message());
                    Log.v("==>call.requestSend", String.valueOf(call.request()));
                    Log.v("==>requestcodeSend", String.valueOf(response.code()));
                    Log.v("==>call.responsebody", String.valueOf(response.body()));
                    Log.v("SuccessSend==>", new Gson().toJson(response.body()));


                    if (TextUtils.equals(String.valueOf(response.code()), "200")) {


                        imagepath = "";
                        helper.clearPreferences("userImage");
                        rl_selectedImage.setVisibility(View.GONE);

                        hasImage = false;
                        if (helper.isConnectingToInternet()){
                            getChat();
                        }
                        et_message.setText("");


                    }else {
                        Toast.makeText(ChatActivity.this, "No Chat Yet", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                }

                @Override
                public void onFailure(Call<BrandInsertResponse> call, Throwable t) {
                    Log.v("Responset", t.toString());

                }
            });

    }

    private void sendMessage() {
        progressDialog.show();

        String userId = UniversalHelper.loadPreferences("userId");
        String authToken = UniversalHelper.loadPreferences("authToken");

        final Intent intent = getIntent();

        Bundle b =intent.getExtras();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userServiceChatId = extras.getString("userChatId");
        }

        ApiConfig getResponse = AppConfig.getRetrofit().create(ApiConfig.class);



        Call<BrandInsertResponse> call = getResponse.addMessageToUserServiceChat(userId,authToken,messageToBeSend,userServiceChatId,hasImage);
        Log.v("==>call.requestSend", call.request().toString());

        call.enqueue(new Callback<BrandInsertResponse>() {
            @Override
            public void onResponse(Call<BrandInsertResponse> call, Response<BrandInsertResponse> response) {
                BrandInsertResponse chatResponce = response.body();
                responseCode = response.code();
                Log.v("==>call.requestSend", call.request().toString());
                Log.v("==>call.responseSend", response.message());
                Log.v("==>call.requestSend", String.valueOf(call.request()));
                Log.v("==>requestcodeSend", String.valueOf(response.code()));
                Log.v("==>call.responsebody", String.valueOf(response.body()));
                Log.v("SuccessSend==>", new Gson().toJson(response.body()));


                if (TextUtils.equals(String.valueOf(response.code()), "200")) {

                    hasImage = false;
                    if (helper.isConnectingToInternet()){
                        getChat();
                    }
                    et_message.setText("");


                }else {
                    Toast.makeText(ChatActivity.this, "No Chat Yet", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<BrandInsertResponse> call, Throwable t) {
                Log.v("Responset", t.toString());

            }
        });

    }

    public void authentication(){


        // chatMessage = et_message.getText().toString();

        if(messageToBeSend.isEmpty()){
            Toast.makeText(ChatActivity.this, "Type a message", Toast.LENGTH_SHORT).show();
        }
        else {
            UniversalHelper.savePreferences("messageToBeSend",messageToBeSend);

            if (helper.isConnectingToInternet()){
                sendMessage();
            }

        }
    }

    public void makeImageFullScreen(String imageUrl){

        rv_chat.setVisibility(View.GONE);
        rl_msgtyping.setVisibility(View.GONE);

        Glide.with(context).load(imageUrl).into(iv_chatImageFull);
        iv_chatImageFull.setVisibility(View.VISIBLE);
        rl_top.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
