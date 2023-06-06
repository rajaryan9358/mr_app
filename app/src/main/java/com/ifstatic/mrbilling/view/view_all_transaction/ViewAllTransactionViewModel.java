package com.ifstatic.mrbilling.view.view_all_transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;

import java.util.List;

public class ViewAllTransactionViewModel extends ViewModel {
    private ViewAllTransactionRepository viewAllTransactionRepository = new ViewAllTransactionRepository();
    private MutableLiveData<List<RecentTransactionModel>> allTransactionListMutableLiveData ;

    public LiveData<List<RecentTransactionModel>> getTransactionFromRepository(boolean isCalledFirstTime){

        if(isCalledFirstTime){

            if(allTransactionListMutableLiveData == null){
                allTransactionListMutableLiveData = viewAllTransactionRepository.getTransactionFromServer();
            }
        } else {
            updateMutableListLiveData(viewAllTransactionRepository.getTransactionFromServer().getValue());
        }
        return allTransactionListMutableLiveData;
    }

    public void updateMutableListLiveData(List<RecentTransactionModel> allTransactionModelList){

        List<RecentTransactionModel> modelList = allTransactionListMutableLiveData.getValue();

        assert modelList!=null;
        modelList.addAll(allTransactionModelList);
        allTransactionListMutableLiveData.setValue(modelList);
    }
}
