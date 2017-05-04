/*
 * Copyright (c) 2017. www.hrsoft.net  Inc. All rights reserved.
 */

package net.hrsoft.voter.network;

import net.hrsoft.voter.VoterApplication;
import net.hrsoft.voter.constant.CacheKey;
import net.hrsoft.voter.constant.Config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API 请求Client
 *
 * @author yuanzeng
 * @since 17/1/22 下午6:40
 */

public class RestClient {
    private static Retrofit retrofit;
    private static VoterService voterService;
    private static OkHttpClient okHttpClient;


    public static VoterService getVoterService() {

        if (voterService == null) {
            voterService = getRetrofit().create(VoterService.class);
        }
        return voterService;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            String token = VoterApplication.getCache().getString(CacheKey.TOKEN);
                            if (token == null) {
                                Request noTokenRequest = chain.request();
                                return chain.proceed(noTokenRequest);
                            }
                            Request request = chain.request().newBuilder()
                                    .header("Token", token)
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return okHttpClient;
    }
}
