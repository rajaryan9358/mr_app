package com.ifstatic.mrbilling.view.view_all_party;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;

import java.util.List;

public class ViewAllPartyViewModel extends ViewModel {

    private ViewAllPartyRepository viewAllPartyRepository = new ViewAllPartyRepository();
    private MutableLiveData<List<MyPartiesModel>> myPartiesListLiveData ;

    public LiveData<List<MyPartiesModel>> getPartiesFromRepository(){

            if(myPartiesListLiveData == null){
                myPartiesListLiveData = viewAllPartyRepository.getPartiesFromServer();
            }
        return myPartiesListLiveData;
    }

    public void updateMutableListLiveData(List<MyPartiesModel> myPartiesModelList){

        List<MyPartiesModel> modelList = myPartiesListLiveData.getValue();

        modelList.addAll(myPartiesModelList);
        myPartiesListLiveData.setValue(modelList);
    }

    public LiveData<List<MyPartiesModel>> getPartiesFromRepositoryAgain(){
        return viewAllPartyRepository.getPartiesFromServer();
    }
}
