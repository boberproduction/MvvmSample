package com.devabit.mvvmsample.model.remote;


import com.devabit.mvvmsample.model.User;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface GitHubApi {
    String URL = "https://api.github.com/";

    @GET("users")
    Single<List<User>> getUsersList();

}
