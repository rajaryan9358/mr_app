package com.ifstatic.mrbilling.view.view_all_transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.comman.models.TransactionModel;

import java.util.List;

public class ViewAllTransactionViewModel extends ViewModel {

    private ViewAllTransactionRepository viewAllTransactionRepository = new ViewAllTransactionRepository();
    private MutableLiveData<List<TransactionModel>> allTransactionListMutableLiveData;

    public LiveData<List<TransactionModel>> getTransactionFromRepository() {

        if (allTransactionListMutableLiveData == null) {
            allTransactionListMutableLiveData = viewAllTransactionRepository.getTransactionFromServer();
        }
        return allTransactionListMutableLiveData;
    }

    public void updateMutableListLiveData(List<TransactionModel> allTransactionModelList) {

        List<TransactionModel> modelList = allTransactionListMutableLiveData.getValue();

        assert modelList != null;
        modelList.addAll(allTransactionModelList);
        allTransactionListMutableLiveData.setValue(modelList);
    }

    public LiveData<List<TransactionModel>> getTransactionsFromRepositoryAgain() {
        return viewAllTransactionRepository.getTransactionFromServer();
    }

    public LiveData<List<TransactionModel>> getTransactionOfSelectedParty(String partyName) {
        return viewAllTransactionRepository.getTransactionOfSelectedPartyFromServer(partyName);
    }
}
