package com.ifstatic.mrbilling.view.create_transaction;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.databinding.ActivityCreateTransactionBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.utilities.AppConstants;
import com.ifstatic.mrbilling.utilities.DateFormat;
import com.ifstatic.mrbilling.utilities.Validation;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;

import java.util.ArrayList;
import java.util.List;

public class CreateTransactionActivity extends AppCompatActivity {

    private ActivityCreateTransactionBinding binding;
    private CreateTransactionViewModel createTransactionViewModel;
    private Dialog progressDialog;
    private String party, amount, paymentMode;
    private List<MyPartiesModel> myPartiesModelList;
    private String currentMrNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
        getBundles();
    }

    private void getBundles() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            myPartiesModelList = bundle.getParcelableArrayList("party_data");
            currentMrNo = bundle.getString("mr_no");

            binding.mrNoTextView.setText("#"+currentMrNo);
            setPartyDataToSpinner();
            System.out.println("============ IN ========== " + myPartiesModelList.size());
        }
    }

    private void setPartyDataToSpinner() {

        ArrayList<String> partyList = new ArrayList<>();

        for (int i = 0; i < myPartiesModelList.size(); i++) {
            partyList.add(myPartiesModelList.get(i).getParty());
        }
        partyList.add(0,"Select Party");
        System.out.println("=================== "+partyList.size());
        ArrayAdapter<String> partySpinnerListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, partyList){

            @Override
            public boolean isEnabled(int position) {
                return position!=0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if(position==0){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextColor(getColor(R.color.black_text_disabled));
                    }
                }
                textView.setTypeface(getTypeFace());
                return view;
            }

        };
        binding.partySpinner.setAdapter(partySpinnerListAdapter);
    }

    private Typeface getTypeFace(){
        Typeface typeface = ResourcesCompat.getFont(this, R.font.nunito_medium);
        return typeface;
    }
    private void initViews() {

        createTransactionViewModel = new ViewModelProvider(this).get(CreateTransactionViewModel.class);
        binding.header.titleTextView.setText("Create Transaction");

        binding.dateTextView.setText(DateFormat.getCurrentDate());
    }

    private void initListeners() {
        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.partySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                try{
                    TextView textView = (TextView) adapterView.getSelectedView();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextColor(getColor(R.color.black_text_secondary));
                    }
                    textView.setTypeface(getTypeFace());
                    if(i!=0){
                        party = adapterView.getSelectedItem().toString();

                        binding.addressTextView.setText(myPartiesModelList.get(i-1).getAddress());
                    }

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                amount = charSequence.toString().trim();
                if (amount.length() == 0) {
                    binding.amountInputLayout.setError("Enter Amount");
                } else {
                    AppBoiler.setInputLayoutErrorDisable(binding.amountInputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton checkedRadio = (RadioButton) findViewById(i);
                int id = radioGroup.getCheckedRadioButtonId();
                switch (i){
                    case R.id.chequeRadioButton:
                        binding.chequeDetailsConstraintLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.onlineRadioButton:
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;
                    case R.id.UPIRadioButton:
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;
                    case R.id.cashRadioButton:
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;
                    case R.id.creditDebitRadioButton:
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;
                }
                paymentMode = checkedRadio.getText().toString();
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Validation.isStringEmpty(party)) {
                    Toast.makeText(CreateTransactionActivity.this, "Select Party", Toast.LENGTH_SHORT).show();

                } else if (Validation.isStringEmpty(amount)) {
                    binding.amountInputLayout.setError("Enter Amount");
                    binding.amountEditText.requestFocus();

                } else if(Validation.isStringEmpty(paymentMode)){
                    Toast.makeText(CreateTransactionActivity.this, "Enter Payment Mode", Toast.LENGTH_SHORT).show();

                } else {
                    if (AppBoiler.isInternetConnected(CreateTransactionActivity.this)) {
                        addTransactionToFirebase();
                    } else {
                        AppBoiler.showSnackBarForInternet(CreateTransactionActivity.this, binding.getRoot());
                    }
                }
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void addTransactionToFirebase() {

        progressDialog = AppBoiler.setProgressDialog(this);

        CreateTransactionModel createTransactionModel = new CreateTransactionModel(party,amount,paymentMode, DateFormat.getCurrentDate(),currentMrNo);

        LiveData<String> responseLiveData = createTransactionViewModel.createTransactionToServer(createTransactionModel);
        responseLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                progressDialog.dismiss();

                if (s.equals(AppConstants.SUCCESS)) {
                    Toast.makeText(CreateTransactionActivity.this, "Transaction Added", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(CreateTransactionActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
