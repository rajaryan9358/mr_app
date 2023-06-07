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
import com.ifstatic.mrbilling.utilities.Validation;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

    private final int dataLimitAtTime = 20; // Parties Data limit at a single time
    private String nodeId;
    private DatabaseReference databaseReference;

    public HomeRepository(){
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }

//    public MutableLiveData<List<PartyModel>> getMyPartiesFromServer(){
//
//        MutableLiveData<List<PartyModel>> myPartiesMutableList = new MutableLiveData<>();
//
//        Query query = databaseReference.child("Party").orderByKey().limitToFirst(8);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                List<PartyModel> modelList = new ArrayList<>();
//
//                if(snapshot.exists()){
//
//                    for(DataSnapshot keySnapshot : snapshot.getChildren()){
//
//                        PartyModel model = keySnapshot.getValue(PartyModel.class);
//                        modelList.add(model);
//                    }
//                }
//                myPartiesMutableList.setValue(modelList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                myPartiesMutableList.setValue(null);
//            }
//        });
//        return myPartiesMutableList;
//    }

    public MutableLiveData<List<TransactionModel>> getRecentTransactionsFromServer(){

        MutableLiveData<List<TransactionModel>> recentTransactionMutableLiveData = new MutableLiveData<>();

        Query query = databaseReference.child("Transaction").orderByKey().limitToLast(3);
        query.addValueEventListener(new ValueEventListener() {
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

    public MutableLiveData<List<PartyModel>> getPartiesFromServer(){

        System.out.println("=========NODE ID ========== "+nodeId);

        MutableLiveData<List<PartyModel>> myPartiesMutableLiveData = new MutableLiveData<>();

        Query query ;

        if(Validation.isStringEmpty(nodeId)){
            query = databaseReference.child("Party").orderByKey().limitToFirst(dataLimitAtTime);
            System.out.println("========= null node ======== ");
        }
        else{
            query = databaseReference.child("Party").orderByKey().startAfter(nodeId).limitToFirst(dataLimitAtTime);
            System.out.println("========= node ======== ");
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<PartyModel> modelList = new ArrayList<>();

                if(snapshot.exists()){

                    for(DataSnapshot nodeSnapshot : snapshot.getChildren()){

                        PartyModel model = nodeSnapshot.getValue(PartyModel.class);
                        modelList.add(model);

                        nodeId = nodeSnapshot.getKey();
                    }
                }
                System.out.println("=============== SIZE ============= "+modelList.size()+"       "+nodeId);

                myPartiesMutableLiveData.setValue(modelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("=========== ERROR ======== "+error.getMessage());
            }
        });
        return myPartiesMutableLiveData;
    }

}
