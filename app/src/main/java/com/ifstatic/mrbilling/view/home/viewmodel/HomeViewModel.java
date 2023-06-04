package com.ifstatic.mrbilling.view.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;
import com.ifstatic.mrbilling.view.home.repo.HomeRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final HomeRepository repository = new HomeRepository();

    public LiveData<List<MyPartiesModel>> getPartiesModelListFromRepository(){
        return repository.getMyPartiesFromServer();
    }

    public LiveData<List<RecentTransactionModel>> getRecentTransactionsFromRepository(){
        return repository.getRecentTransactionsFromServer();
    }

}
