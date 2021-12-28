package com.mala.transco;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqRepository {

    private FaqService faqService;

    public FaqRepository() {
        this.faqService = TranscoService.faqService();
    }

    public MutableLiveData<HttpResponse<List<Faq>>> getFaqs() {
        MutableLiveData<HttpResponse<List<Faq>>> faqsM = new MutableLiveData<>();
        Call<HttpResponse<List<Faq>>> httpResponseCall = this.faqService.getFaqs();
        httpResponseCall.enqueue(new Callback<HttpResponse<List<Faq>>>() {
            @Override
            public void onResponse(Call<HttpResponse<List<Faq>>> call, Response<HttpResponse<List<Faq>>> response) {
                Log.d("FAQ_DATA", "onResponse: " + new Gson().toJson(response.body()));
                HttpResponse<List<Faq>> hr = response.body();
                if (hr != null) {
                    faqsM.postValue(hr);
                } else {
                    hr = new HttpResponse();
                    hr.setCode("500");
                    hr.setMessage("Echec de connexion au serveur");
                    faqsM.postValue(hr);
                }
            }

            @Override
            public void onFailure(Call<HttpResponse<List<Faq>>> call, Throwable t) {

                Log.d("FAQ_DATA", "onResponse: " + t.getMessage());

                HttpResponse hr = new HttpResponse();
                hr.setCode("500");
                hr.setMessage("Echec de connexion au serveur");
                faqsM.postValue(hr);
            }
        });
        return faqsM;
    }
}
