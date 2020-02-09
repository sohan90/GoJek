package com.gojek.application.di.module;

import android.app.Application;

import com.blankj.utilcode.util.NetworkUtils;
import com.gojek.application.BuildConfig;
import com.gojek.application.retrofit.Api;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    private static final long RETROFIT_TIMEOUT = 10;

    private static final String PRAGMA = "Pragma";

    @Singleton
    @Provides
    Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient.Builder okHttpBuilder) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return retrofitBuilder.client(okHttpBuilder.build()).build();
    }

    @Singleton
    @Provides
    Cache provideCache(Application application) {
        int cacheSize = (5 * 1024 * 1024);   //cache size is 5mb
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (NetworkUtils.isConnected()) {
                        request.newBuilder()
                                .header("Cache-Control", "public, max-age=" + 5)
                                .removeHeader(PRAGMA);
                    } else {
                        request = request.newBuilder()
                                .removeHeader(PRAGMA)
                                .addHeader("Cache-Control", //cache should expire after 2 hours
                                        "public, only-if-cached, max-stale=" + 60 * 60 * 2)
                                .build();
                    }

                    return chain.proceed(request);
                });


        return okHttpBuilder;
    }

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLogging() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


}
