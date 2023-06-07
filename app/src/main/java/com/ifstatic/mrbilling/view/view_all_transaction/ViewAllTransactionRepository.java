package com.ifstatic.mrbilling.view.view_all_transaction;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;
import com.ifstatic.mrbilling.utilities.Validation;

import java.util.ArrayList;
import java.util.List;

public class ViewAllTransactionRepository {

    private DatabaseReference databaseReference;
    private final int dataLimitAtTime = 20;
    private String nodeId;

    public ViewAllTransactionRepository(){
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }

    public MutableLiveData<List<TransactionModel>> getTransactionFromServer(){

        System.out.println("=========NODE ID ========== "+nodeId);

        MutableLiveData<List<TransactionModel>> allTransactionListMutableLiveData = new MutableLiveData<>();

        Query query ;
        if(Validation.isStringEmpty(nodeId)){
            query = databaseReference.child("Transaction").orderByKey().limitToFirst(dataLimitAtTime);
            System.out.println("========= null node ======== ");
        }
        else{
            query = databaseReference.child("Party").orderByKey().startAfter(nodeId).limitToFirst(dataLimitAtTime);
            System.out.println("========= node ======== ");
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<TransactionModel> modelList = new ArrayList<>();

                if(snapshot.exists()){

                    for(DataSnapshot nodeSnapshot : snapshot.getChildren()){

                        TransactionModel model = nodeSnapshot.getValue(TransactionModel.class);
                        modelList.add(model);

                        nodeId = nodeSnapshot.getKey();
                    }
                }
                System.out.println("=============== SIZE ============= "+modelList.size()+"       "+nodeId);

                allTransactionListMutableLiveData.setValue(modelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("=========== ERROR ======== "+error.getMessage());
            }
        });
        return allTransactionListMutableLiveData;
    }

    public MutableLiveData<List<TransactionModel>> getTransactionOfSelectedPartyFromServer(String partyName){

        MutableLiveData<List<TransactionModel>> allTransactionListMutableLiveData = new MutableLiveData<>();

        databaseReference.child("Transaction").orderByChild("party").startAt(partyName.toUpperCase()).endAt(partyName.toLowerCase()+"\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        List<TransactionModel> modelList = new ArrayList<>();

                        if(snapshot.exists()){
                            System.out.println("======== found ========= ");

                            for(DataSnapshot keySnapshot : snapshot.getChildren()){
                                System.out.println("======== Children count ========= "+snapshot.getChildrenCount());
                                modelList.add(keySnapshot.getValue(TransactionModel.class));
                            }
                        }
                        allTransactionListMutableLiveData.setValue(modelList);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("======== ERROR ========= "+error.getMessage());
                    }
                });

        return allTransactionListMutableLiveData;
    }
}
