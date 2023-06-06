package com.ifstatic.mrbilling.view.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;
import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.comman.models.TransactionModel;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

    private DatabaseReference databaseReference;

    public HomeRepository(){
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }

    public MutableLiveData<List<PartyModel>> getMyPartiesFromServer(){

        MutableLiveData<List<PartyModel>> myPartiesMutableList = new MutableLiveData<>();

        Query query = databaseReference.child("Party").orderByKey().limitToFirst(8);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<PartyModel> modelList = new ArrayList<>();

                if(snapshot.exists()){

                    for(DataSnapshot keySnapshot : snapshot.getChildren()){

                        PartyModel model = keySnapshot.getValue(PartyModel.class);
                        modelList.add(model);
                    }
                }
                myPartiesMutableList.setValue(modelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myPartiesMutableList.setValue(null);
            }
        });
        return myPartiesMutableList;
    }

    public MutableLiveData<List<TransactionModel>> getRecentTransactionsFromServer(){

        MutableLiveData<List<TransactionModel>> recentTransactionMutableLiveData = new MutableLiveData<>();
        databaseReference.child("Transaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<TransactionModel> modelList = new ArrayList<>();
                if(snapshot.exists()){

                    for(DataSnapshot keySnapshot : snapshot.getChildren()){

                        TransactionModel model = keySnapshot.getValue(TransactionModel.class);
                        modelList.add(model);
                    }
                }
                recentTransactionMutableLiveData.setValue(modelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                recentTransactionMutableLiveData.setValue(null);
            }
        });
        return recentTransactionMutableLiveData;
    }
}
