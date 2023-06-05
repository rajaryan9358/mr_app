package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.databinding.ActivityTransactionDetailBinding;
import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;

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