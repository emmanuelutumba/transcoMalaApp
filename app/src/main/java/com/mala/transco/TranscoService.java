package com.mala.transco;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranscoService {

    private static Retrofit retrofit;

    public TranscoService() {
    }

    private static Retrofit newInstance() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static FaqService faqService() {
        newInstance();
        return retrofit.create(FaqService.class);
    }

    public static MessageService messageService() {
        newInstance();
        return retrofit.create(MessageService.class);
    }
}
