package com.ifstatic.mrbilling.view.home;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ifstatic.mrbilling.databinding.ActivityTransactionDetailBinding;


public class TransactionDetailActivity extends AppCompatActivity {

    private ActivityTransactionDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
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