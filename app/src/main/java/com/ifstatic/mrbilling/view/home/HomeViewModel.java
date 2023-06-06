package com.ifstatic.mrbilling.view.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.view.home.HomeRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final HomeRepository repository = new HomeRepository();

    private LiveData<List<PartyModel>> myPartiesListLiveData;
    private LiveData<List<TransactionModel>> recentTransactionListLiveData;

    public LiveData<List<PartyModel>> getPartiesModelListFromRepository(){

        if(myPartiesListLiveData == null){
            myPartiesListLiveData =  repository.getMyPartiesFromServer();
        }
        return myPartiesListLiveData;
    }

    public LiveData<List<TransactionModel>> getRecentTransactionsFromRepository(){

        if(recentTransactionListLiveData == null){
            recentTransactionListLiveData =  repository.getRecentTransactionsFromServer();
        }
        return recentTransactionListLiveData;
    }

}
