package com.gojek.application.di.module;

import com.gojek.application.BuildConfig;
import com.gojek.application.retrofit.Api;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class HttpModule {

    private static final long RETROFIT_TIMEOUT = 60;

    @Singleton
    @Provides
    public Api provideApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient.Builder okHttpBuilder) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.HOST_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return retrofitBuilder.client(okHttpBuilder.build()).build();
    }

    @Singleton
    @Provides
    public OkHttpClient.Builder provideOkHttpBuilder(HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(RETROFIT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder();

                    Request additionalRequest = requestBuilder.build();
                    return chain.proceed(additionalRequest);
                })
                .addInterceptor(loggingInterceptor);

        return okHttpBuilder;
    }

    @Singleton
    @Provides
    public HttpLoggingInterceptor provideHttpLogging() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


}
