package com.mala.transco;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FaqFragment extends Fragment {

    RecyclerView faqs;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    private FaqViewModel faqViewModel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.faqViewModel = new ViewModelProvider(requireActivity()).get(FaqViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);

        faqs = view.findViewById(R.id.faqs);

        MyFaqRecyclerViewAdapter myFaqRecyclerViewAdapter = new MyFaqRecyclerViewAdapter();
        myFaqRecyclerViewAdapter.setFaqs(new ArrayList<>());
        faqs.setAdapter(myFaqRecyclerViewAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        faqs.setLayoutManager(linearLayoutManager);

        this.refresh.setRefreshing(true);
        this.faqViewModel.getFaqs().observe(requireActivity(), listHttpResponse -> {
            this.refresh.setRefreshing(false);
            if (listHttpResponse.getCode().equals("200")) {
                myFaqRecyclerViewAdapter.setFaqs(listHttpResponse.getData());
                myFaqRecyclerViewAdapter.notifyDataSetChanged();
            }
        });

        myFaqRecyclerViewAdapter.setCallback(data -> {
            NavDirections navDirections = FaqFragmentDirections.actionFaqFragmentToMessageFragment(new Gson().toJson(data));
            this.navController.navigate(navDirections);
        });

        this.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                faqViewModel.getFaqs().observe(requireActivity(), listHttpResponse -> {
                    refresh.setRefreshing(false);
                    if (listHttpResponse.getCode().equals("200")) {
                        myFaqRecyclerViewAdapter.setFaqs(listHttpResponse.getData());
                        myFaqRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.navController = Navigation.findNavController(view);
    }
}