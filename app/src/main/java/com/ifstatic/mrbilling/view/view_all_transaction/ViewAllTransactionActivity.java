package com.ifstatic.mrbilling.view.view_all_transaction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ifstatic.mrbilling.comman.adapters.TransactionAdapter;
import com.ifstatic.mrbilling.databinding.ActivityViewAllTransactionBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.view.transaction_detail.TransactionDetailActivity;

import java.util.List;

public class ViewAllTransactionActivity extends AppCompatActivity {

    private ActivityViewAllTransactionBinding binding;
    private TransactionAdapter transactionAdapter;
    private ViewAllTransactionViewModel viewAllTransactionViewModel;

    private boolean isDataFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllTransactionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
        setTransactionAdapter();
        getTransactionFromViewModel();
    }

    private void initViews() {
        binding.header.titleTextView.setText("All Transactions");
        viewAllTransactionViewModel = new ViewModelProvider(this).get(ViewAllTransactionViewModel.class);
    }

    private void initListeners() {

        binding.header.backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* ======== Finding last item of recycler view for calling api again for data ====== */
        binding.recentTransactionRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible+1 >= totalItemCount;
                System.out.println("========= "+lastVisible+"     "+totalItemCount+"     "+endHasBeenReached);

                if (totalItemCount > 0 && endHasBeenReached) {

                    if(isDataFound){
                        getAgainTransactionListFromViewModel();
                        isDataFound = false;
                        System.out.println("===== LAST ITEM OF RECYCLER VIEW ========== ");
                    }
                }
            }
        });
    }


    private void getTransactionFromViewModel(){

        LiveData<List<TransactionModel>> viewAllTransactionLiveData = viewAllTransactionViewModel.getTransactionFromRepository();
        viewAllTransactionLiveData.observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModels) {

                isDataFound = true;

                if(transactionModels == null){
                    System.out.println("=========== NULLABLE ============ ");
                    return;
                }
                notifyRecentTransactionAdapter(transactionModels);
            }
        });
    }


    private void setTransactionAdapter() {

        transactionAdapter = new TransactionAdapter(this);
        binding.recentTransactionRecyclerView.setAdapter(transactionAdapter);

        transactionAdapter.initItemClickListener(new TransactionAdapter.TransactionListClickListener() {
            @Override
            public void onClickItem(TransactionModel model, int position) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("transaction_data",model);
                AppBoiler.navigateToActivity(ViewAllTransactionActivity.this, TransactionDetailActivity.class,bundle);

            }
        });
    }

    private void notifyRecentTransactionAdapter(List<TransactionModel> transactionModelList) {
        transactionAdapter.notifyListItemChanged(transactionModelList);
    }

    private void getAgainTransactionListFromViewModel(){

        LiveData<List<TransactionModel>> viewAllTransactionLiveData = viewAllTransactionViewModel.getTransactionsFromRepositoryAgain();
        viewAllTransactionLiveData.observe(this, new Observer<List<TransactionModel>>() {
            @Override
            public void onChanged(List<TransactionModel> transactionModelList) {
                viewAllTransactionViewModel.updateMutableListLiveData(transactionModelList);
            }
        });
    }
}