package com.ifstatic.mrbilling.view.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;
import com.ifstatic.mrbilling.view.home.adapters.RecentTransactionAdapter;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;

import java.util.List;

public class ViewAllTransactionActivity extends AppCompatActivity {

    private ActivityViewAllTransactionBinding binding;
    private RecentTransactionAdapter recentTransactionAdapter;
    private List<RecentTransactionModel> recentTransactionModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
        setRecentTransactionAdapter();

        notifyRecentTransactionAdapter(recentTransactionModelList);
    }

    private void initListeners() {

        binding.header.titleTextView.setText("All Transactions");

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setRecentTransactionAdapter() {

        recentTransactionAdapter = new RecentTransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);
    }

    private void notifyRecentTransactionAdapter(List<RecentTransactionModel> recentTransactionModelList) {
        recentTransactionAdapter.notifyItemChanged(recentTransactionModelList);
    }

}