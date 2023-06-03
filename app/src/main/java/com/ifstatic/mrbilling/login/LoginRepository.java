package com.ifstatic.mrbilling.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ifstatic.mrbilling.repository.local.SharedPrefHelper;
import com.ifstatic.mrbilling.utilities.AppConstants;



public class LoginRepository {

    private MutableLiveData<String> responseMutableData = new MutableLiveData<>();

    public MutableLiveData<String> loginUser(String email , String password){

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                responseMutableData.setValue(AppConstants.SUCCESS);
                System.out.println("======== USER ID======= "+authResult.getUser().getUid());

                SharedPrefHelper.getInstance().setUidOfUser(authResult.getUser().getUid());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                responseMutableData.setValue(e.getMessage());
                System.out.println("=================== Exception while Registration : "+e.getMessage());
            }
        });
        return responseMutableData;
    }

}
