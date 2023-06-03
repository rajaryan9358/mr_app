package com.ifstatic.mrbilling.view.add_party;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.ifstatic.mrbilling.databinding.ActivityAddPartyBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.utilities.AppConstants;
import com.ifstatic.mrbilling.utilities.Validation;

public class AddPartyActivity extends AppCompatActivity {

    private ActivityAddPartyBinding binding;
    private AddPartyViewModel addPartyViewModel;
    private Dialog progressDialog;
    private String party , address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
    }

    private void initViews() {

        addPartyViewModel = new ViewModelProvider(this).get(AddPartyViewModel.class);
        binding.header.titleTextView.setText("Add Party");
    }

    private void initListeners() {

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        binding.partyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                party = charSequence.toString().trim();
                if(party.length() == 0){
                    binding.partyInputLayout.setError("Enter Party Name");
                } else {
                    AppBoiler.setInputLayoutErrorDisable(binding.partyInputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                address = charSequence.toString().trim();
                if(address.length() == 0){
                    binding.addressInputLayout.setError("Enter Address");
                } else {
                    AppBoiler.setInputLayoutErrorDisable(binding.addressInputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Validation.isStringEmpty(party)){
                    binding.partyInputLayout.setError("Enter Party Name");
                    binding.partyEditText.requestFocus();

                } else if(Validation.isStringEmpty(address)){
                    binding.addressInputLayout.setError("Enter Address");
                    binding.addressEditText.requestFocus();

                } else {

                    if(AppBoiler.isInternetConnected(AddPartyActivity.this)){
                        addPartyToFirebase();
                    } else{
                        AppBoiler.showSnackBarForInternet(AddPartyActivity.this,binding.getRoot());
                    }
                }
            }
        });
    }

    private void addPartyToFirebase() {

        progressDialog = AppBoiler.setProgressDialog(this);

        AddPartyModel addPartyModel = new AddPartyModel(party,address);
        LiveData<String> responseLiveData = addPartyViewModel.addPartyResponseLiveData(addPartyModel);

        responseLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();

                if(s.equals(AppConstants.SUCCESS)){
                    Toast.makeText(AddPartyActivity.this, "Party Added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(AddPartyActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}