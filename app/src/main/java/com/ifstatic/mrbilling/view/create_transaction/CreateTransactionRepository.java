package com.ifstatic.mrbilling.view.create_transaction;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;
import com.ifstatic.mrbilling.utilities.AppConstants;


public class CreateTransactionRepository {

    public MutableLiveData<String> addTransactionToServer(TransactionModel model){

        MutableLiveData<String> responseMutableLiveData = new MutableLiveData<>();

        DatabaseReference databaseReference = FirebaseHelper.getInstance().getDatabaseReference();

        databaseReference.child("Transaction").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                responseMutableLiveData.setValue(AppConstants.SUCCESS);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseMutableLiveData.setValue(AppConstants.FAILED);
                System.out.println("======== ERROR ========= "+e.getMessage());
            }
        });
        return responseMutableLiveData;
    }
}
