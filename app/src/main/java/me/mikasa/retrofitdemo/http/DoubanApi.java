package me.mikasa.retrofitdemo.http;

import me.mikasa.retrofitdemo.bean.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DoubanApi {
    @GET("top250")
    Call<ResponseEntity>getMovie();
}
