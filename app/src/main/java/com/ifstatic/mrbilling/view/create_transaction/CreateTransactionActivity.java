package com.ifstatic.mrbilling.view.create_transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ifstatic.mrbilling.databinding.ActivityCreateTransactionBinding;

public class CreateTransactionActivity extends AppCompatActivity {

    private ActivityCreateTransactionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding  = ActivityCreateTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}