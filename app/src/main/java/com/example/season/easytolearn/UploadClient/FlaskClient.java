package com.example.season.easytolearn.UploadClient;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Season on 2017/5/9.
 */

public interface FlaskClient {

    //上传图片
    @Multipart
    @POST("/uploadImages")
    Call<UploadResult> uploadMultipleFiles(
            @PartMap Map<String,RequestBody> files);

    //上传文本
    @Multipart
    @POST("/uploadFile")
    Call<ResponseBody> uploadFiles(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part files);

    //上传音频
    @Multipart
    @POST("/uploadAudio")
    Call<ResponseBody> uploadAudio(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part files);


}