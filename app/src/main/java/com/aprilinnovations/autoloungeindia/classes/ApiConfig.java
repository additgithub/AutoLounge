package com.aprilinnovations.autoloungeindia.classes;


import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandInsertResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.BrandListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.ChatResponce;
import com.aprilinnovations.autoloungeindia.retrofitResponse.EmergencyContactsResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.GetChatResponce;
import com.aprilinnovations.autoloungeindia.retrofitResponse.LoginResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.ModelListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.NotificationDataResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.NotificationListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.ServiceNameListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserCarListResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserCarServiceInfoResponse;
import com.aprilinnovations.autoloungeindia.retrofitResponse.UserProfile;
import com.aprilinnovations.autoloungeindia.retrofitResponse.VarientListResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface ApiConfig {

    @POST("auth/login")
    Call<LoginResponse> userLogin(@Query("deviceType") String deviceType, @Query("phoneNumber") String phoneNumber,@Query("firebaseToken") String firebaseToken);

    @GET("auth/logout")
    Call<LoginResponse> userLogout(@Query("userId") String userId, @Query("authToken") String authToken);

    @Multipart
    @POST("auth/profile_post")
    Call<UserProfile> updateProfile(@Part MultipartBody.Part profileImage, @Query("userName") String userName, @Query("emailId") String emailId, @Query("userId") String userId, @Query("authToken") String authToken);

    @GET("auth/profile")
    Call<UserProfile> getProfile(@Query("userId") String userId, @Query("authToken") String authToken, @Query("firebaseToken") String firebaseToken);

    @GET("car/brand")
    Call<BrandListResponse> getBrandList(@Query("userId") String userId, @Query("authToken") String authToken);

    @GET("car/model")
    Call<ModelListResponse> getModelList(@Query("userId") String userId, @Query("authToken") String authToken, @Query("carBrand") String carBrand);

    @GET("car/variant")
    Call<VarientListResponse> getVarientList(@Query("userId") String userId, @Query("authToken") String authToken, @Query("carBrand") String carBrand, @Query("carModel") String carModel);

    @POST("car")
    Call<BrandInsertResponse> addNewCar(@Query("userId") String userId, @Query("authToken") String authToken, @Query("carDataId") String carDataId, @Query("carRegistrationNumber") String carRegistrationNumber,
                                        @Query("dateOfPurchase") String dateOfPurchase, @Query("kilometers") String kilometers);

    @GET("service/list")
    Call<LoginResponse> getServiceTypeList(@Query("userId") String userId, @Query("authToken") String authToken);

    @GET("service/name")
    Call<ServiceNameListResponse> getServiceNameList(@Query("userId") String userId, @Query("authToken") String authToken, @Query("serviceType") String serviceType);

    @GET("car")
    Call<UserCarListResponse> getUserCar(@Query("userId") String userId, @Query("authToken") String authToken);

    @GET("car/my_car")
    Call<UserCarListResponse> getUserCarData(@Query("userId") String userId, @Query("authToken") String authToken);

    @POST("service")
    Call<BrandInsertResponse> addUserService(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userCarDataId") String userCarDataId,
                                       @Query("serviceType") String serviceType, @Query("serviceName") String serviceName,
                                       @Query("pickUpLocation") String pickUpLocation, @Query("pickUpRequire") String pickUpRequire,
                                       @Query("pickUpTime") String pickUpTime);

    @POST("service")
    Call<BrandInsertResponse> addUserServiceNoPickUp(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userCarDataId") String userCarDataId,
                                       @Query("serviceType") String serviceType, @Query("serviceName") String serviceName,
                                       @Query("pickUpLocation") String pickUpLocation, @Query("pickUpRequire") String pickUpRequire);

    @GET("car/service")
    Call<UserCarServiceInfoResponse> getUserCarServiceInfo(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userCarDataId") String userCarDataId);

    @GET("notification/list")
    Call<NotificationListResponse> getNotificationList(@Query("userId") String userId, @Query("authToken") String authToken, @Query("count") String count);

    @GET("notification")
    Call<NotificationDataResponse> getOneNotification(@Query("userId") String userId, @Query("authToken") String authToken, @Query("notificationDataId") String notificationDataId);

    @POST("car/update")
    Call<BrandInsertResponse> updateCarInfo(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userCarDataId") String userCarDataId, @Query("kilometers") String kilometers, @Query("dateOfPurchase") String dateOfPurchase, @Query("carRegistrationNumber") String carRegistrationNumber);

    @DELETE("car/delete")
    Call<BrandInsertResponse> deleteUserCar(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userCarDataId") String userCarDataId);

    @GET("chat")
    Call<GetChatResponce> getUserServiceChatData(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userServiceChatId") String userServiceChatId);

    @Multipart
    @POST("chat/chat_post")
    Call<BrandInsertResponse> addMessageToUserServiceChatImage(@Part MultipartBody.Part profileImage,@Query("userId") String userId, @Query("authToken") String authToken, @Query("message") String message, @Query("userServiceChatId") String userServiceChatId,@Query("hasImage") boolean hasImage);

    @POST("chat/chat_post")
    Call<BrandInsertResponse> addMessageToUserServiceChat(@Query("userId") String userId, @Query("authToken") String authToken, @Query("message") String message, @Query("userServiceChatId") String userServiceChatId,@Query("hasImage") boolean hasImage);

    @GET("emergency")
    Call<EmergencyContactsResponse> getEmergencyContactList(@Query("userId") String userId, @Query("authToken") String authToken);

    @GET("emergency")
    Call<LoginResponse> getRealTimeChat(@Query("userId") String userId, @Query("authToken") String authToken, @Query("userServiceChatId") String userServiceChatId,@Query("createTime") String createTime );





}

