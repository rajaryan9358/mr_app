package com.ifstatic.mrbilling.view.add_party;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.comman.models.PartyModel;

public class AddPartyViewModel extends ViewModel {

    private final AddPartyRepository repository = new AddPartyRepository();

    public LiveData<String> addPartyResponseLiveData(PartyModel model){
        return repository.addPartyToServer(model);
    }
}
