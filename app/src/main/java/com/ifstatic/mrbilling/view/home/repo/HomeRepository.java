package com.ifstatic.mrbilling.view.home.repo;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ifstatic.mrbilling.repository.remote.FirebaseHelper;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {

    private DatabaseReference databaseReference;

    public HomeRepository(){
        databaseReference = FirebaseHelper.getInstance().getDatabaseReference();
    }


    public MutableLiveData<List<MyPartiesModel>> getMyPartiesFromServer(){

        MutableLiveData<List<MyPartiesModel>> myPartiesMutableList = new MutableLiveData<>();

        databaseReference.child("Party").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    List<MyPartiesModel> modelList = new ArrayList<>();
                    for(DataSnapshot keySnapshot : snapshot.getChildren()){

                        MyPartiesModel model = keySnapshot.getValue(MyPartiesModel.class);
                        modelList.add(model);
                    }
                    myPartiesMutableList.setValue(modelList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return myPartiesMutableList;
    }
}
