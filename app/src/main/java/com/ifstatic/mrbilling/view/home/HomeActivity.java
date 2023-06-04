package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;
import com.ifstatic.mrbilling.view.home.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;
    private MyPartiesAdapter myPartiesAdapter;
    private RecentTransactionAdapter recentTransactionAdapter;
    private List<MyPartiesModel> myPartiesModelList;
    private String currentMrNo ;
    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();

        setMyPartiesAdapter();
        setRecentTransactionAdapter();

        if(AppBoiler.isInternetConnected(this)){

            progressDialog = AppBoiler.setProgressDialog(this);
            getRecentTransactionFromServer();
            getMyPartiesFromServer();

        } else {
            AppBoiler.showSnackBarForInternet(this,binding.getRoot());
        }

    }

    private void initViews() {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    private void initListeners() {

        binding.addTransactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentMrNo == null){
                    currentMrNo = "0";
                }
                int no = Integer.parseInt(currentMrNo);
                no++;

                Bundle bundle = new Bundle();
                bundle.putString("mr_no",String.valueOf(no));
                bundle.putParcelableArrayList("party_data", (ArrayList<? extends Parcelable>) myPartiesModelList);
                AppBoiler.navigateToActivity(HomeActivity.this, CreateTransactionActivity.class,bundle);
            }
        });

        binding.addPartyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppBoiler.navigateToActivity(HomeActivity.this, AddPartyActivity.class,null);
            }
        });
    }


    /* ======================================= RECENT TRANSACTIONS ========================================  */

    private void getRecentTransactionFromServer(){

        LiveData<List<RecentTransactionModel>> recentTransactionModelLiveData = homeViewModel.getRecentTransactionsFromRepository();
        recentTransactionModelLiveData.observe(this, new Observer<List<RecentTransactionModel>>() {
            @Override
            public void onChanged(List<RecentTransactionModel> recentTransactionModels) {
                if(recentTransactionModels == null){
                    Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                 if(recentTransactionModels.size() > 0){
                     currentMrNo = recentTransactionModels.get(recentTransactionModels.size()-1).getMrNo();
                     System.out.println("================= CURRENT MR ========= "+currentMrNo);
                     binding.viewAllTransactionTextView.setVisibility(View.VISIBLE);
                } else {
                    binding.viewAllTransactionTextView.setVisibility(View.GONE);
                }
                 notifyRecentTransactionAdapter(recentTransactionModels);
            }
        });

    }
    private void setRecentTransactionAdapter() {

        recentTransactionAdapter = new RecentTransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(recentTransactionAdapter);
    }

    private void notifyRecentTransactionAdapter(List<RecentTransactionModel> recentTransactionModelList){
        recentTransactionAdapter.notifyItemChanged(recentTransactionModelList);
    }


    /* ======================================= MY PARTIES ========================================  */

    private void getMyPartiesFromServer(){

        LiveData<List<MyPartiesModel>> myPartiesModelListLiveData = homeViewModel.getPartiesModelListFromRepository();
        myPartiesModelListLiveData.observe(this, new Observer<List<MyPartiesModel>>() {
            @Override
            public void onChanged(List<MyPartiesModel> myPartiesModels) {

                progressDialog.dismiss();

                if(myPartiesModels.size() >0){
                    binding.noPartiesFoundTextView.setVisibility(View.GONE);
                    binding.viewAllPartyTextView.setVisibility(View.VISIBLE);
                } else {
                    binding.noPartiesFoundTextView.setVisibility(View.VISIBLE);
                    binding.viewAllPartyTextView.setVisibility(View.GONE);
                }
                myPartiesModelList = myPartiesModels;
                notifyPartiesAdapter();
            }
        });
    }

    private void setMyPartiesAdapter(){

        myPartiesAdapter = new MyPartiesAdapter(this);
        binding.myPartiesRecyclerView.setAdapter(myPartiesAdapter);

        myPartiesAdapter.initClickListener(new MyPartiesAdapter.ItemClickListener() {
            @Override
            public void onClickItem(int position) {

            }
        });
    }

    private void notifyPartiesAdapter() {
        myPartiesAdapter.notifyListIsChanged(myPartiesModelList);
    }

}