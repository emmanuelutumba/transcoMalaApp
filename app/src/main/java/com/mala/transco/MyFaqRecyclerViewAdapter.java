package com.mala.transco;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyFaqRecyclerViewAdapter extends RecyclerView.Adapter<MyFaqRecyclerViewAdapter.ViewHolder> {

    private List<Faq> faqs = new ArrayList<>();
    private Callback callback;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    public void setFaqs(List<Faq> faqs) {
        this.faqs = faqs;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item.setOnClickListener(view -> {
            if (callback != null)
                callback.onSuccess(faqs.get(position));
        });
        holder.description.setText(faqs.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return faqs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout item;
        public TextView description;

        public ViewHolder(View view) {
            super(view);
            item = view.findViewById(R.id.item);
            description = view.findViewById(R.id.description);
        }
    }
}