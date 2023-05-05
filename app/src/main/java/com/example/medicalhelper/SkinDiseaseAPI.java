package com.example.medicalhelper;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SkinDiseaseAPI {
    @Multipart
    @POST("facebody/analysis/detect-skin-disease")
    Call<ResponseBody> detectSkinDisease(
            @Part("image\"; filename=\"image.jpg\"") RequestBody file,
            @Header("X-RapidAPI-Key") String apiKey,
            @Header("X-RapidAPI-Host") String apiHost
    );
}
