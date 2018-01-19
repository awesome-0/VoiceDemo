package com.example.samuel.voicedemo;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Samuel on 13/12/2017.
 */

public interface MakeCall {

    @POST("call")
    @FormUrlEncoded()
    Call<RequestBody> makeCall (
            @HeaderMap Map<String,String> header,
            @Field("username") String username,
            @Field("from") String from,
            @Field("to") String to
    );

    @POST("get")
    @FormUrlEncoded
    Call<ResponseBody> sayWords (
          @Field("word") String word
    );

    @POST("get")
    @FormUrlEncoded
    Call<ResponseBody> PostRedialNumber (
            @Field("number") String number
    );
}
