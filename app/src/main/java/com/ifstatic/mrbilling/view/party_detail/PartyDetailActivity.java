package com.ifstatic.mrbilling.view.party_detail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ifstatic.mrbilling.comman.adapters.TransactionAdapter;
import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.databinding.ActivityPartyDetailsBinding;

import java.util.List;


public class PartyDetailActivity extends AppCompatActivity {

    private ActivityPartyDetailsBinding binding;

    private PartyModel partyModel;
    private PartyDetailViewModel partyDetailViewModel;
    private TransactionAdapter transactionAdapter;

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
            partyModel = bundle.getParcelable("party_data");
        }
    }

    private void initViews() {
        partyDetailViewModel = new ViewModelProvider(this).get(PartyDetailViewModel.class);

        binding.header.titleTextView.setText("Party Details");
        binding.addressTextView.setText(partyModel.getAddress());
        binding.partyNameTextView.setText(partyModel.getParty());

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

    private void getTransactionOfPartyFromViewModel() {

        LiveData<List<TransactionModel>> transactionLiveData = partyDetailViewModel.getTransactionListFromRepository(partyModel.getParty());
        transactionLiveData.observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModelList) {
                notifyAdapter(transactionModelList);
            }
        });
    }

    private void setAdapterForTransaction() {
        transactionAdapter = new TransactionAdapter(this);
        binding.partyDetailRecyclerView.setAdapter(transactionAdapter);
    }

    private void notifyAdapter(List<TransactionModel> transactionModelList) {
        transactionAdapter.notifyListItemChanged(transactionModelList);
    }
}