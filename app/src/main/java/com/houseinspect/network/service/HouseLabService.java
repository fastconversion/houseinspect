package com.houseinspect.network.service;


import com.houseinspect.model.supportModel.Company;
import com.houseinspect.model.HouseDataModel;
import com.houseinspect.model.SubmitHouseResult;
import com.houseinspect.model.FormModel.RegisterData;
import com.houseinspect.model.SuccessResponse;
import com.houseinspect.network.utils.Callback;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by Lalit on 6/28/2016.
 */
public interface HouseLabService {

    @GET("/getCompanyList")
    void loadComapnies(Callback<List<Company>> cb);

    @Multipart
    @POST("/register")
    void registerNow(@Part("identity_image") TypedFile idFile, @Part("signature_image") TypedFile signatureFile,
                     @Part("user_info") String userInfo,
                     Callback<SuccessResponse> callback);

    @FormUrlEncoded
    @POST("/login")
    void doLogin(@Field("username") String username, @Field("password") String password, Callback<RegisterData> cb);

    @FormUrlEncoded
    @POST("/addnewinspection")
    void uploadInspectionData(@Field("data") String data, @Field("uid") String uidOfData,
                              @Field("finalApprovalReport") String submissionData,
                              @Field("start_time") Long startTime,
                              @Field("end_time") Long endTime,Callback<SubmitHouseResult> cb);

    @FormUrlEncoded
    @POST("/getHouseInspectionNonSubsidy")
    void getNonSubHouseData(@Field("uid") String uid, @Field("userRole") String userRole, @Field("userId") String userId,
                            Callback<HouseDataModel> cb);

    @FormUrlEncoded
    @POST("/updateProfile")
    void updateProfile(@Field("data") String data, Callback<SuccessResponse> cb);

    @FormUrlEncoded
    @POST("/updatePassword")
    void changePassword(@Field("user_id") String userId,@Field("current_password") String oldPass,
                        @Field("new_password") String newPass, Callback<SuccessResponse> cb);
    @FormUrlEncoded
    @POST("/forgetpassword")
    void forgotEmail(@Field("email") String emailId, Callback<SuccessResponse> cb);

    @FormUrlEncoded
    @POST("/updatefcmid")
    void updateFCMID(@Field("fcmid") String fcmId, @Field("user_id") String userId, Callback<SuccessResponse> cb);
}
