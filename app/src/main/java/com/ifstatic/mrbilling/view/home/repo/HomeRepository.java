package com.ifstatic.mrbilling.view.home.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

    private DatabaseReference databaseReference;

    public HomeRepository(){
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }


    public MutableLiveData<List<MyPartiesModel>> getMyPartiesFromServer(){

        MutableLiveData<List<MyPartiesModel>> myPartiesMutableList = new MutableLiveData<>();

        Query query = databaseReference.child("Party").orderByKey().limitToFirst(8);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<MyPartiesModel> modelList = new ArrayList<>();

                if(snapshot.exists()){

                    for(DataSnapshot keySnapshot : snapshot.getChildren()){

                        MyPartiesModel model = keySnapshot.getValue(MyPartiesModel.class);
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

    public MutableLiveData<List<RecentTransactionModel>> getRecentTransactionsFromServer(){

        MutableLiveData<List<RecentTransactionModel>> recentTransactionMutableLiveData = new MutableLiveData<>();
        databaseReference.child("Transaction").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<RecentTransactionModel> modelList = new ArrayList<>();
                if(snapshot.exists()){

                    for(DataSnapshot keySnapshot : snapshot.getChildren()){

                        RecentTransactionModel model = keySnapshot.getValue(RecentTransactionModel.class);
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
