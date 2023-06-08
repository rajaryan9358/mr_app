package com.ifstatic.mrbilling.view.transaction_detail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.databinding.ActivityTransactionDetailBinding;


public class TransactionDetailActivity extends AppCompatActivity {

    private ActivityTransactionDetailBinding binding;
    private TransactionModel transactionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
        getBundles();
        initViews();
    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            transactionModel = bundle.getParcelable("transaction_data");
        }
    }

    private void initViews() {

        if (transactionModel != null) {

            binding.mrNoTextView.setText("#" + transactionModel.getMrNo());
            binding.dateTextView.setText(transactionModel.getDate());
            binding.nameTextView.setText(transactionModel.getParty());
            binding.addressTextView.setText(transactionModel.getAddress());
            binding.amountTextView.setText(transactionModel.getAmount());
            binding.paymentModeTextView.setText(transactionModel.getPaymentMode());

            if (transactionModel.getChequeDetail() != null) {
                binding.datePaymentDetailTextView.setText(transactionModel.getChequeDetail().getDate());
                binding.bankNameTextView.setText(transactionModel.getChequeDetail().getBankName());
                binding.chequeNoTextView.setText(transactionModel.getChequeDetail().getChequeNo());
            } else {
                binding.paymentDetailsLayout.setVisibility(View.GONE);
                binding.paymentDetailsTextView.setVisibility(View.GONE);
            }

        }
    }

    private void initListeners() {

        binding.header.titleTextView.setText("Transaction Details");

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}