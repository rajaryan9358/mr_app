package com.ifstatic.mrbilling.view.party_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.ifstatic.mrbilling.databinding.ActivityPartyDetailsBinding;
import com.ifstatic.mrbilling.view.home.adapters.RecentTransactionAdapter;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;

import java.util.List;


public class PartyDetailsActivity extends AppCompatActivity {

    private ActivityPartyDetailsBinding binding;

    private MyPartiesModel myPartiesModel;
    private PartyDetailViewModel partyDetailViewModel;
    private RecentTransactionAdapter recentTransactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityPartyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
        getBundles();
        initViews();
        getTransactionOfPartyFromViewModel();
        setAdapterForTransaction();
    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myPartiesModel = bundle.getParcelable("party_data");
        }
    }

    private void initViews(){
        partyDetailViewModel = new ViewModelProvider(this).get(PartyDetailViewModel.class);

        binding.header.titleTextView.setText("Party Details");
        binding.addressTextView.setText(myPartiesModel.getAddress());
        binding.partyNameTextView.setText(myPartiesModel.getParty());

        binding.headerRecentTransaction.partyTextView.setVisibility(View.GONE);
    }

    private void initListeners() {

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getTransactionOfPartyFromViewModel(){

        LiveData<List<RecentTransactionModel>> transactionLiveData = partyDetailViewModel.getTransactionListFromRepository(myPartiesModel.getParty());
        transactionLiveData.observe(this, new Observer<List<RecentTransactionModel>>() {
            @Override
            public void onChanged(List<RecentTransactionModel> recentTransactionModelList) {
                notifyAdapter(recentTransactionModelList);
            }
        });
    }

    private void setAdapterForTransaction(){
        recentTransactionAdapter = new RecentTransactionAdapter(this);
        binding.partyDetailRecyclerView.setAdapter(recentTransactionAdapter);
    }

    private void notifyAdapter(List<RecentTransactionModel> recentTransactionModelList){
        recentTransactionAdapter.notifyItemChanged(recentTransactionModelList);
    }
}