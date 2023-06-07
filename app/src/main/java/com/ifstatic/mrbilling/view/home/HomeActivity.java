package com.ifstatic.mrbilling.view.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.ifstatic.mrbilling.comman.adapters.PartyAdapter;
import com.ifstatic.mrbilling.comman.adapters.TransactionAdapter;
import com.ifstatic.mrbilling.databinding.ActivityHomeBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.view.add_party.AddPartyActivity;
import com.ifstatic.mrbilling.view.create_transaction.CreateTransactionActivity;
import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.view.party_detail.PartyDetailActivity;
import com.ifstatic.mrbilling.view.transaction_detail.TransactionDetailActivity;
import com.ifstatic.mrbilling.view.view_all_party.ViewAllMyPartyActivity;
import com.ifstatic.mrbilling.view.view_all_transaction.ViewAllTransactionActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private HomeViewModel homeViewModel;
    private PartyAdapter partyAdapter;
    private TransactionAdapter transactionAdapter;
    private List<PartyModel> partyModelList;
    private String currentMrNo ;
    private Dialog progressDialog;
    private boolean isDataFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();

        setMyPartiesAdapter();
        setRecentTransactionAdapter();


            progressDialog = AppBoiler.setProgressDialog(this);
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

                if(currentMrNo == null){
                    currentMrNo = "0";
                }
                int no = Integer.parseInt(currentMrNo);
                no++;

                Bundle bundle = new Bundle();
                bundle.putString("mr_no",String.valueOf(no));
                bundle.putParcelableArrayList("party_data", (ArrayList<? extends Parcelable>) partyModelList);
                AppBoiler.navigateToActivity(HomeActivity.this, CreateTransactionActivity.class,bundle);
            }
        });

        /* ======== Finding last item of recycler view for calling api again for data ====== */
        binding.myPartiesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible+1 >= totalItemCount;
                System.out.println("========= "+lastVisible+"     "+totalItemCount+"     "+endHasBeenReached);

                if(lastVisible == totalItemCount -1 ){
//                if (totalItemCount > 0 && endHasBeenReached) {

                     /* if searching is not activated then only get all data
                       else shows only searched data list.
                     */
                    if(isDataFound){

                        getPartyAgainFromViewModel();
                        isDataFound = false;
                        System.out.println("===== LAST ITEM OF RECYCLER VIEW ========== ");
                    }
                }
            }
        });

//        binding.addPartyButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppBoiler.navigateToActivity(HomeActivity.this, AddPartyActivity.class,null);
//            }
//        });
//        binding.viewAllTransactionTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppBoiler.navigateToActivity(HomeActivity.this, ViewAllTransactionActivity.class,null);
//            }
//        });
//        binding.viewAllPartyTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppBoiler.navigateToActivity(HomeActivity.this, ViewAllMyPartyActivity.class,null);
//            }
//        });
    }


    /* ======================================= RECENT TRANSACTIONS ========================================  */

    private void getRecentTransactionFromServer(){

        LiveData<List<TransactionModel>> recentTransactionModelLiveData = homeViewModel.getRecentTransactionsFromRepository();
        recentTransactionModelLiveData.observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {
                if(transactionModels == null){
                    Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    return;
                }
                 if(transactionModels.size() > 0){
                     currentMrNo = transactionModels.get(transactionModels.size()-1).getMrNo();
                     System.out.println("================= CURRENT MR ========= "+currentMrNo);
                    // binding.viewAllTransactionTextView.setVisibility(View.VISIBLE);
                } else {
                   // binding.viewAllTransactionTextView.setVisibility(View.GONE);
                }
                 notifyRecentTransactionAdapter(transactionModels);
            }
        });
    }

    private void setRecentTransactionAdapter() {

        transactionAdapter = new TransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(transactionAdapter);

        transactionAdapter.initItemClickListener(new TransactionAdapter.TransactionListClickListener() {
            @Override
            public void onClickItem(TransactionModel model, int position) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("transaction_data",model);
                AppBoiler.navigateToActivity(HomeActivity.this, TransactionDetailActivity.class,bundle);
            }
        });
    }

    private void notifyRecentTransactionAdapter(List<TransactionModel> transactionModelList){
        transactionAdapter.notifyListItemChanged(transactionModelList);
    }


    /* ======================================= MY PARTIES ========================================  */

    private void getMyPartiesFromServer(){

        LiveData<List<PartyModel>> myPartiesModelListLiveData = homeViewModel.getPartiesModelListFromRepository();
        myPartiesModelListLiveData.observe(this, new Observer<List<PartyModel>>() {
            @Override
            public void onChanged(List<PartyModel> partyModels) {

                isDataFound = true;
                progressDialog.dismiss();

                if(partyModels.size() >0){
                    binding.noPartiesFoundTextView.setVisibility(View.GONE);
                   // binding.viewAllPartyTextView.setVisibility(View.VISIBLE);
                } else {
                    binding.noPartiesFoundTextView.setVisibility(View.VISIBLE);
                  //  binding.viewAllPartyTextView.setVisibility(View.GONE);
                }
                partyModelList = partyModels;
                notifyPartiesAdapter();
            }
        });
    }

    private void setMyPartiesAdapter(){

        partyAdapter = new PartyAdapter(this);
        binding.myPartiesRecyclerView.setAdapter(partyAdapter);

//        partyAdapter.initItemClickListener(new PartyAdapter.PartyItemClickListener() {
//            @Override
//            public void onClickItem(int position, PartyModel model) {
//
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("party_data",model);
//                AppBoiler.navigateToActivity(HomeActivity.this, PartyDetailActivity.class,bundle);
//            }
//        });
    }

    private void notifyPartiesAdapter() {
        partyAdapter.notifyListIsChanged(partyModelList);
    }

    private void getPartyAgainFromViewModel() {

        LiveData<List<PartyModel>> myPartiesLiveData = homeViewModel.getPartiesFromRepositoryAgain();
        myPartiesLiveData.observe(this, new Observer<List<PartyModel>>() {
            @Override
            public void onChanged(List<PartyModel> partyModelList) {
                homeViewModel.updatePartyMutableListLiveData(partyModelList);
            }
        });
    }
}