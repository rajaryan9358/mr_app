package com.ifstatic.mrbilling.view.view_all_transaction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.view.home.adapters.MyPartiesAdapter;
import com.ifstatic.mrbilling.view.home.adapters.RecentTransactionAdapter;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;
import com.ifstatic.mrbilling.view.party_detail.PartyDetailsActivity;
import com.ifstatic.mrbilling.view.view_all_party.ViewAllMyPartyActivity;

import java.util.List;

public class ViewAllTransactionActivity extends AppCompatActivity {

    private ActivityViewAllTransactionBinding binding;
    private RecentTransactionAdapter recentTransactionAdapter;
    private List<RecentTransactionModel> recentTransactionModelList;

    private ViewAllTransactionViewModel viewAllTransactionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
        setTransactionAdapter();

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
    private void getTransactionFromViewModel(boolean isCalledFirstTime){

        LiveData<List<RecentTransactionModel>> viewAllTransactionLiveData = viewAllTransactionViewModel.getTransactionFromRepository(isCalledFirstTime);
        viewAllTransactionLiveData.observe(this, new Observer<List<RecentTransactionModel>>() {
            @Override
            public void onChanged(List<RecentTransactionModel> recentTransactionModels) {

                if(recentTransactionModels == null){
                    System.out.println("=========== NULLABLE ============ ");
                    return;
                } else if(!isCalledFirstTime){
                    viewAllTransactionViewModel.updateMutableListLiveData(recentTransactionModels);
                }
                notifyRecentTransactionAdapter(recentTransactionModels);
            }
        });
    }


    private void setTransactionAdapter() {

        recentTransactionAdapter = new RecentTransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);


    }

    private void notifyRecentTransactionAdapter(List<RecentTransactionModel> recentTransactionModelList) {
        recentTransactionAdapter.notifyItemChanged(recentTransactionModelList);
    }

}