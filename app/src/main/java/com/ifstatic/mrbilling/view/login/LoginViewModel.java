package com.ifstatic.mrbilling.view.login;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository = new LoginRepository();

    public LiveData<String> getResponseLiveData(String email, String password) {
        return loginRepository.loginUser(email, password);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        System.out.println("======================= Login view model destroyed");
    }
}
