package com.mala.transco;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MessageService {
    @POST("message")
    Call<HttpResponse<Message>> send(@Body Message message);
}
