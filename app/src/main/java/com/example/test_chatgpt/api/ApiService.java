package com.example.test_chatgpt.api;

import com.example.test_chatgpt.login.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    // https://642eadfa2b883abc64141693.mockapi.io/login/dangnhap
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH-mm-ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://642eadfa2b883abc64141693.mockapi.io/login/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("dangnhap")
    Call<List<User>> getUsers();
}
