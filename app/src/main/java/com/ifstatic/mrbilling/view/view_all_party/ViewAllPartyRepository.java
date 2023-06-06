package com.ifstatic.mrbilling.view.view_all_party;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;
import com.ifstatic.mrbilling.utilities.Validation;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllPartyRepository {

    private DatabaseReference databaseReference;
    private final int dataLimitAtTime = 2;
    private String nodeId;

    public ViewAllPartyRepository(){
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }

    public MutableLiveData<List<MyPartiesModel>> getPartiesFromServer(){

        System.out.println("=========NODE ID ========== "+nodeId);

        MutableLiveData<List<MyPartiesModel>> myPartiesMutableLiveData = new MutableLiveData<>();

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

                List<MyPartiesModel> modelList = new ArrayList<>();

                if(snapshot.exists()){

                    for(DataSnapshot nodeSnapshot : snapshot.getChildren()){

                        MyPartiesModel model = nodeSnapshot.getValue(MyPartiesModel.class);
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
