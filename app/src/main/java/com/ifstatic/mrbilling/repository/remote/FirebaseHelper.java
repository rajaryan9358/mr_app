package com.ifstatic.mrbilling.repository.remote;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ifstatic.mrbilling.utilities.AppConstants;

public class FirebaseHelper {

   private static FirebaseHelper firebaseHelper;
   private DatabaseReference databaseReference;
   private StorageReference storageReference;

    private FirebaseHelper(){
        databaseReference = FirebaseDatabase.getInstance().getReference(AppConstants.APP_NAME);
        storageReference = FirebaseStorage.getInstance().getReference(AppConstants.APP_NAME);
    }

    public static FirebaseHelper getInstance(){
        if(firebaseHelper == null){
            firebaseHelper = new FirebaseHelper();
        }
        return firebaseHelper;
    }

    public DatabaseReference getDatabaseReference(){
        return databaseReference;
    }

    public StorageReference getStorageReference(){
        return storageReference;
    }
}
