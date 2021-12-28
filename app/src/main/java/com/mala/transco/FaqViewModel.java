package com.mala.transco;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class FaqViewModel extends AndroidViewModel {

    private FaqRepository faqRepository;

    public FaqViewModel(@NonNull Application application) {
        super(application);
        this.faqRepository = new FaqRepository();
    }

    public MutableLiveData<HttpResponse<List<Faq>>> getFaqs() {
        return faqRepository.getFaqs();
    }

}
