package com.mala.transco;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FaqService {

    @GET("faq")
    Call<HttpResponse<List<Faq>>> getFaqs();

}
