package com.ifstatic.mrbilling.view.add_party;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddPartyViewModel extends ViewModel {

    private final AddPartyRepository repository = new AddPartyRepository();

    public LiveData<String> addPartyResponseLiveData(AddPartyModel model){
        return repository.addPartyToServer(model);
    }
}
