package com.ifstatic.mrbilling.view.create_transaction;

import android.annotation.SuppressLint;
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
import com.ifstatic.mrbilling.view.create_transaction.models.ChequeDetailModel;
import com.ifstatic.mrbilling.view.create_transaction.models.CreateTransactionModel;
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
            if(myPartiesModelList != null){
                setPartyDataToSpinner();
            }
        }
    }

    /* ============================ SET PARTY ADAPTER ==================================== */

    private void setPartyDataToSpinner() {

        ArrayList<String> partyList = new ArrayList<>();

        for (int i = 0; i < myPartiesModelList.size(); i++) {
            partyList.add(myPartiesModelList.get(i).getParty());
        }
        partyList.add(0,"Select Party");

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


    /* ============================ INITIALIZER ==================================== */

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
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                try{
                    TextView textView = (TextView) adapterView.getSelectedView();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        textView.setTextColor(getColor(R.color.black_text_secondary));
                    }
                    textView.setTypeface(getTypeFace());

                    if(position!=0){
                        party = adapterView.getSelectedItem().toString();
                        binding.addressTextView.setText(myPartiesModelList.get(position-1).getAddress());
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
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton checkedRadio = (RadioButton) findViewById(i);
                int id = radioGroup.getCheckedRadioButtonId();

                switch (i){
                    case R.id.chequeRadioButton:
                        paymentMode = "Cheque";
                        binding.chequeDetailsConstraintLayout.setVisibility(View.VISIBLE);
                        break;

                    case R.id.onlineRadioButton:
                        paymentMode = "Online";
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;

                    case R.id.UPIRadioButton:
                        paymentMode = "UPI";
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;

                    case R.id.cashRadioButton:
                        paymentMode = "Cash";
                        binding.chequeDetailsConstraintLayout.setVisibility(View.GONE);
                        break;

                    case R.id.creditDebitRadioButton:
                        paymentMode = "Credit or Debit";
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

                    CreateTransactionModel createTransactionModel = new CreateTransactionModel(party,amount,paymentMode, DateFormat.getCurrentDate(),currentMrNo);

                    switch (paymentMode){
                        case "Cheque" :

                            String date = binding.dateEditText.getText().toString().trim();
                            String bankName = binding.bankNameEditText.getText().toString().trim();
                            String chequeNo = binding.chequeNoEditText.getText().toString().trim();

                            if(isChequeDetailValidate(date,bankName,chequeNo)) {

                            ChequeDetailModel chequeDetailModel = new ChequeDetailModel(date,bankName,chequeNo);
                            createTransactionModel.setChequeDetail(chequeDetailModel);

                            createTransactionToServer(createTransactionModel);
                           }
                           break;
                        case "Online": createTransactionToServer(createTransactionModel);
                            break;
                        case "UPI":createTransactionToServer(createTransactionModel);
                            break;
                        case "Cash": createTransactionToServer(createTransactionModel);
                            break;
                        case "Credit or Debit": createTransactionToServer(createTransactionModel);
                            break;
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

    private void createTransactionToServer(CreateTransactionModel createTransactionModel) {

        if (AppBoiler.isInternetConnected(CreateTransactionActivity.this)) {

            progressDialog = AppBoiler.setProgressDialog(this);

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

        } else {
            AppBoiler.showSnackBarForInternet(CreateTransactionActivity.this, binding.getRoot());
        }
    }

    private boolean isChequeDetailValidate(String date , String bankName , String chequeNo){

        if(Validation.isStringEmpty(date)){
            binding.dateInputLayout.setError("Select Date");
            binding.dateEditText.requestFocus();
            return false;

        } else if(Validation.isStringEmpty(bankName)){
            AppBoiler.setInputLayoutErrorDisable(binding.dateInputLayout);
            binding.bankNameInputLayout.setError("Enter Bank Name");
            binding.bankNameEditText.requestFocus();
            return false;

        } else if(Validation.isStringEmpty(chequeNo)){
            AppBoiler.setInputLayoutErrorDisable(binding.bankNameInputLayout);
            binding.chequeNoInputLayout.setError("Enter Cheque Number");
            binding.chequeNoEditText.requestFocus();
            return false;
        } else{
            AppBoiler.setInputLayoutErrorDisable(binding.chequeNoInputLayout);
            return true;
        }

    }
}
