package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.databinding.ActivityViewAllMyPartyBinding;
import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;

public class ViewAllMyPartyActivity extends AppCompatActivity {

    private ActivityViewAllMyPartyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllMyPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
    }
    private void initListeners() {

        binding.header.titleTextView.setText("My Parties");

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}