package com.gojek.application.retrofit;

import com.gojek.application.model.TrendingResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("/repositories")
    Single<List<TrendingResponse>> getTrendingGitHub();
}
