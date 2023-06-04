package com.ifstatic.mrbilling.view.create_transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


public class CreateTransactionViewModel extends ViewModel {
    private final CreateTransactionRepository repository = new CreateTransactionRepository();

    public LiveData<String> createTransactionToServer(CreateTransactionModel model){
        return repository.addTransactionToServer(model);
    }

}
