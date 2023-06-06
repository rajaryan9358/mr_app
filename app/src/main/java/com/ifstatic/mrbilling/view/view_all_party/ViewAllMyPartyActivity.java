package com.ifstatic.mrbilling.view.view_all_party;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.View;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.databinding.ActivityViewAllMyPartyBinding;
import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.view.home.adapters.MyPartiesAdapter;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.party_detail.PartyDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ViewAllMyPartyActivity extends AppCompatActivity {

    private ActivityViewAllMyPartyBinding binding;
    private ViewAllPartyViewModel viewAllPartyViewModel;
    private MyPartiesAdapter myPartiesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllMyPartyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
        setPartyAdapter();
        getPartyFromViewModel();
    }

    private void initViews() {
        binding.header.titleTextView.setText("My Parties");
        viewAllPartyViewModel = new ViewModelProvider(this).get(ViewAllPartyViewModel.class);
    }

    private void initListeners() {

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPartyAgainFromViewModel();
                //finish();
            }
        });
    }

    private void getPartyFromViewModel(){

        LiveData<List<MyPartiesModel>> myPartiesLiveData = viewAllPartyViewModel.getPartiesFromRepository();
        myPartiesLiveData.observe(this, new Observer<List<MyPartiesModel>>() {
            @Override
            public void onChanged(List<MyPartiesModel> myPartiesModels) {

                if(myPartiesModels == null){
                    System.out.println("=========== NULLABLE ============ ");
                    return;
                }
                notifyAdapter(myPartiesModels);
            }
        });
    }

    private void setPartyAdapter(){
        myPartiesAdapter = new MyPartiesAdapter(this);
        binding.myPartiesRecyclerView.setAdapter(myPartiesAdapter);

        myPartiesAdapter.initClickListener(new MyPartiesAdapter.ItemClickListener() {
            @Override
            public void onClickItem(int position,MyPartiesModel model) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("party_data",model);

                AppBoiler.navigateToActivity(ViewAllMyPartyActivity.this, PartyDetailsActivity.class,bundle);
            }
        });
    }

    private void notifyAdapter(List<MyPartiesModel> myPartiesModelList) {
        myPartiesAdapter.notifyListIsChanged(myPartiesModelList);
    }

    private void getPartyAgainFromViewModel(){

        LiveData<List<MyPartiesModel>> myPartiesLiveData = viewAllPartyViewModel.getPartiesFromRepositoryAgain();
        myPartiesLiveData.observe(this, new Observer<List<MyPartiesModel>>() {
            @Override
            public void onChanged(List<MyPartiesModel> myPartiesModelList) {
                viewAllPartyViewModel.updateMutableListLiveData(myPartiesModelList);
            }
        });
    }

}