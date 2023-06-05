package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.databinding.ActivityHomeBinding;
import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;
import com.ifstatic.mrbilling.view.home.adapters.RecentTransactionAdapter;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;
import com.ifstatic.mrbilling.view.home.viewmodel.HomeViewModel;

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
    private void notifyRecentTransactionAdapter(List<RecentTransactionModel> recentTransactionModelList){
        recentTransactionAdapter.notifyItemChanged(recentTransactionModelList);
    }

}