package com.ifstatic.mrbilling.view.view_all_transaction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.ifstatic.mrbilling.comman.adapters.TransactionAdapter;
import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.view.transaction_detail.TransactionDetailActivity;

import java.util.List;

public class ViewAllTransactionActivity extends AppCompatActivity {

    private ActivityViewAllTransactionBinding binding;
    private TransactionAdapter transactionAdapter;
    private List<TransactionModel> transactionModelList;
    private ViewAllTransactionViewModel viewAllTransactionViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
        setTransactionAdapter();

        notifyRecentTransactionAdapter(transactionModelList);
    }

    private void initViews() {
        binding.header.titleTextView.setText("All Transactions");
    }

    private void initListeners() {

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void getTransactionFromViewModel(){

        LiveData<List<TransactionModel>> viewAllTransactionLiveData = viewAllTransactionViewModel.getTransactionFromRepository();
        viewAllTransactionLiveData.observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {


            }
        });
    }


    private void setTransactionAdapter() {

        transactionAdapter = new TransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(transactionAdapter);

        transactionAdapter.initItemClickListener(new TransactionAdapter.TransactionListClickListener() {
            @Override
            public void onClickItem(TransactionModel model, int position) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("transaction_data",model);
                AppBoiler.navigateToActivity(ViewAllTransactionActivity.this, TransactionDetailActivity.class,bundle);

            }
        });
    }

    private void notifyRecentTransactionAdapter(List<TransactionModel> transactionModelList) {
        transactionAdapter.notifyListItemChanged(transactionModelList);
    }
}