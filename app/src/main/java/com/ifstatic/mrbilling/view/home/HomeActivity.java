package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.databinding.ActivityHomeBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.view.add_party.AddPartyActivity;
import com.ifstatic.mrbilling.view.create_transaction.CreateTransactionActivity;
import com.ifstatic.mrbilling.view.home.adapters.MyPartiesAdapter;
import com.ifstatic.mrbilling.view.home.adapters.RecentTransactionAdapter;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;
import com.ifstatic.mrbilling.view.home.viewmodel.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
        getRecentTransactionFromServer();
        getMyPartiesFromServer();
    }

    private void initViews() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initListeners() {

        binding.addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBoiler.navigateToActivity(HomeActivity.this, CreateTransactionActivity.class,null);
            }
        });

        binding.addPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBoiler.navigateToActivity(HomeActivity.this, AddPartyActivity.class,null);
            }
        });
    }

    private void getRecentTransactionFromServer(){

    }

    private void getMyPartiesFromServer(){

        LiveData<List<MyPartiesModel>> myPartiesModelListLiveData = homeViewModel.getPartiesModelListFromRepository();
        myPartiesModelListLiveData.observe(this, new Observer<List<MyPartiesModel>>() {
            @Override
            public void onChanged(List<MyPartiesModel> myPartiesModels) {
                if(myPartiesModels.size() >0){
                    setMyPartiesAdapter(myPartiesModels);
                } else {
                    Toast.makeText(HomeActivity.this, "No Parties Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setRecentTransactionAdapter() {

        RecentTransactionAdapter recentTransactionAdapter = new RecentTransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);
    }

    private void setMyPartiesAdapter(List<MyPartiesModel> myPartiesModelList) {

        MyPartiesAdapter myPartiesAdapter = new MyPartiesAdapter(this , myPartiesModelList);
        binding.myPartiesRecyclerView.setAdapter(myPartiesAdapter);

        myPartiesAdapter.initClickListener(new MyPartiesAdapter.ItemClickListener() {
            @Override
            public void onClickItem(int position) {

            }
        });
    }
}