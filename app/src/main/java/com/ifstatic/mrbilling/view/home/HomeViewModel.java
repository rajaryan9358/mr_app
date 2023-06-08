package com.ifstatic.mrbilling.view.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.comman.models.TransactionModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final HomeRepository repository = new HomeRepository();

    private MutableLiveData<List<PartyModel>> myPartiesListLiveData;
    private LiveData<List<TransactionModel>> recentTransactionListLiveData;

    public LiveData<List<PartyModel>> getPartiesModelListFromRepository() {

        if (myPartiesListLiveData == null) {
            //  myPartiesListLiveData =  repository.getMyPartiesFromServer();
            myPartiesListLiveData = repository.getPartiesFromServer();

        }
        return myPartiesListLiveData;
    }

    public LiveData<List<PartyModel>> getPartiesFromRepositoryAgain() {
        return repository.getPartiesFromServer();
    }

    public void updatePartyMutableListLiveData(List<PartyModel> partyModelList) {

        List<PartyModel> modelList = myPartiesListLiveData.getValue();

        modelList.addAll(partyModelList);
        myPartiesListLiveData.setValue(modelList);
    }


    public LiveData<List<TransactionModel>> getRecentTransactionsFromRepository() {

        if (recentTransactionListLiveData == null) {
            recentTransactionListLiveData = repository.getRecentTransactionsFromServer();
        }
        return recentTransactionListLiveData;
    }


}
