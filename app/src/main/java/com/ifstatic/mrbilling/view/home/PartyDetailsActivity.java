package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ifstatic.mrbilling.databinding.ActivityPartyDetailsBinding;


public class PartyDetailsActivity extends AppCompatActivity {

    private ActivityPartyDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPartyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();

    }
    private void initListeners() {

        binding.header.titleTextView.setText("Party Details");

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}