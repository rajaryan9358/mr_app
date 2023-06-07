package com.ifstatic.mrbilling.view.view_all_party;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.comman.models.TransactionModel;

import java.util.List;

public class ViewAllPartyViewModel extends ViewModel {

    private ViewAllPartyRepository viewAllPartyRepository = new ViewAllPartyRepository();
    private MutableLiveData<List<PartyModel>> myPartiesListLiveData ;

    public LiveData<List<PartyModel>> getPartiesFromRepository(){

            if(myPartiesListLiveData == null){
                myPartiesListLiveData = viewAllPartyRepository.getPartiesFromServer();
            }
        return myPartiesListLiveData;
    }

    public void updateMutableListLiveData(List<PartyModel> partyModelList){

        List<PartyModel> modelList = myPartiesListLiveData.getValue();

        modelList.addAll(partyModelList);
        myPartiesListLiveData.setValue(modelList);
    }

    public LiveData<List<PartyModel>> getPartiesFromRepositoryAgain(){
        return viewAllPartyRepository.getPartiesFromServer();
    }

    public LiveData<List<PartyModel>> getSelectedPartyFromRepository(String partyName){
        return viewAllPartyRepository.getSelectedPartyFromServer(partyName);
    }
}
