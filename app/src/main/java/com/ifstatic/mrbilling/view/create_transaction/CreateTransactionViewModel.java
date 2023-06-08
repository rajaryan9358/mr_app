package com.ifstatic.mrbilling.view.create_transaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ifstatic.mrbilling.comman.models.TransactionModel;


public class CreateTransactionViewModel extends ViewModel {
    private final CreateTransactionRepository repository = new CreateTransactionRepository();

    public LiveData<String> createTransactionToServer(TransactionModel model) {
        return repository.addTransactionToServer(model);
    }

}
