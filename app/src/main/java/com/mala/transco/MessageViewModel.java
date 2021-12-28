package com.mala.transco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepostory messageRepostory;


    public MessageViewModel(@NonNull Application application) {
        super(application);
        this.messageRepostory = new MessageRepostory();
    }

    public MutableLiveData<HttpResponse<Message>> send(Message message) {
        return this.messageRepostory.send(message);
    }
}
