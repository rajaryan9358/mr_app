package com.ifstatic.mrbilling.view.login;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ifstatic.mrbilling.databinding.ActivityLoginBinding;
import com.ifstatic.mrbilling.repository.local.SharedPrefHelper;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.utilities.AppConstants;
import com.ifstatic.mrbilling.utilities.Validation;
import com.ifstatic.mrbilling.view.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    private String email = "", password = "";
    public Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (isUserLoggedIn()) {
            AppBoiler.navigateToActivityWithFinish(this, HomeActivity.class, null);
        }

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        initListeners();

    }

    private void initListeners() {

        binding.userIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = charSequence.toString().trim();
                int isValid = Validation.isValidEmail(email);

                if (isValid == 1) {
                    binding.userIdInputLayout.setError("Enter User Id");
                } else if (isValid == 2) {
                    binding.userIdInputLayout.setError("Invalid format");
                } else if (isValid == 0) {
                    AppBoiler.setInputLayoutErrorDisable(binding.userIdInputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.passwordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                password = charSequence.toString().trim();
                if (Validation.isStringEmpty(password)) {
                    binding.passwordInputLayout.setError("Enter password");
                } else {
                    AppBoiler.setInputLayoutErrorDisable(binding.passwordInputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (binding.userIdInputLayout.isErrorEnabled()) {
                    binding.userIdEditText.requestFocus();
                } else if (Validation.isStringEmpty(email)) {
                    binding.userIdInputLayout.setError("Enter User Id");
                    binding.userIdEditText.requestFocus();
                } else if (binding.passwordInputLayout.isErrorEnabled() || Validation.isStringEmpty(password)) {
                    binding.passwordEditText.requestFocus();
                    binding.passwordInputLayout.setError("Enter Password");
                } else {
                    loginUser();
                }
            }
        });
    }

    private void loginUser() {
        if (AppBoiler.isInternetConnected(this)) {
            progressDialog = AppBoiler.setProgressDialog(LoginActivity.this);
            observeResponseOfLogin();
        } else {
            AppBoiler.showSnackBarForInternet(this, binding.rootLayoutOfLogin);
        }
    }

    private void observeResponseOfLogin() {

        LiveData<String> responseLiveData = loginViewModel.getResponseLiveData(email, password);

        responseLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                progressDialog.dismiss();
                if (s.equals(AppConstants.SUCCESS)) {
                    AppBoiler.navigateToActivityWithFinish(LoginActivity.this, HomeActivity.class, null);
                } else {
                    Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isUserLoggedIn() {
        String uid = SharedPrefHelper.getInstance().getUidOfUser();
        if (Validation.isStringEmpty(uid)) {
            return false;
        } else
            return true;
    }

}