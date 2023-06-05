package com.ifstatic.mrbilling.view.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;
import com.ifstatic.mrbilling.view.home.repo.HomeRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final HomeRepository repository = new HomeRepository();

    private LiveData<List<MyPartiesModel>> myPartiesListLiveData;
    private LiveData<List<RecentTransactionModel>> recentTransactionListLiveData;

    public LiveData<List<MyPartiesModel>> getPartiesModelListFromRepository(){

        if(myPartiesListLiveData == null){
            myPartiesListLiveData =  repository.getMyPartiesFromServer();
        }
        return myPartiesListLiveData;
    }

    public LiveData<List<RecentTransactionModel>> getRecentTransactionsFromRepository(){

        if(recentTransactionListLiveData == null){
            recentTransactionListLiveData =  repository.getRecentTransactionsFromServer();
        }
        return recentTransactionListLiveData;
    }

}
