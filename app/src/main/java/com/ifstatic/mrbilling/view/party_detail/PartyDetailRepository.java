package com.ifstatic.mrbilling.view.party_detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class PartyDetailRepository {

    private DatabaseReference databaseReference;

    public PartyDetailRepository() {
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }

    public MutableLiveData<List<TransactionModel>> getTransactionFromServer(String partyName) {

        MutableLiveData<List<TransactionModel>> transactionMutableLiveData = new MutableLiveData<>();

        databaseReference.child("Transaction").orderByChild("party").equalTo(partyName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<TransactionModel> modelList = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot keySnapshot : snapshot.getChildren()) {

                        TransactionModel model = keySnapshot.getValue(TransactionModel.class);
                        modelList.add(model);
                    }
                    transactionMutableLiveData.setValue(modelList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("=========== ERROR ======== ");
                transactionMutableLiveData.setValue(null);
            }
        });
        return transactionMutableLiveData;
    }
}
