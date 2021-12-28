package com.mala.transco;

import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageRepostory {
    private MessageService messageService;

    public MessageRepostory() {
        this.messageService = TranscoService.messageService();
    }

    public MutableLiveData<HttpResponse<Message>> send(Message message) {
        MutableLiveData<HttpResponse<Message>> messageM = new MutableLiveData<>();

        Call<HttpResponse<Message>> httpResponseCall = this.messageService.send(message);
        httpResponseCall.enqueue(new Callback<HttpResponse<Message>>() {
            @Override
            public void onResponse(Call<HttpResponse<Message>> call, Response<HttpResponse<Message>> response) {
                HttpResponse<Message> hr = response.body();
                if (hr != null) {
                    messageM.postValue(hr);
                } else {
                    hr = new HttpResponse();
                    hr.setCode("500");
                    hr.setMessage("Echec de connexion au serveur");
                    messageM.postValue(hr);
                }
            }

            @Override
            public void onFailure(Call<HttpResponse<Message>> call, Throwable t) {
                HttpResponse hr = new HttpResponse();
                hr.setCode("500");
                hr.setMessage("Echec de connexion au serveur");
                messageM.postValue(hr);
            }
        });
        return messageM;
    }
}
