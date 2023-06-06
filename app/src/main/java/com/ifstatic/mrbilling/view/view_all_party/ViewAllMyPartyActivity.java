package com.ifstatic.mrbilling.view.view_all_party;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.ifstatic.mrbilling.comman.adapters.PartyAdapter;
import com.ifstatic.mrbilling.databinding.ActivityViewAllMyPartyBinding;
import com.ifstatic.mrbilling.utilities.AppBoiler;
import com.ifstatic.mrbilling.comman.models.PartyModel;
import com.ifstatic.mrbilling.view.party_detail.PartyDetailActivity;

import java.util.List;

public class ViewAllMyPartyActivity extends AppCompatActivity {

    private ActivityViewAllMyPartyBinding binding;
    private ViewAllPartyViewModel viewAllPartyViewModel;
    private PartyAdapter partyAdapter;

    private boolean isDataFound = false;

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
                finish();
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

                if (totalItemCount > 0 && endHasBeenReached) {

                    if(isDataFound){
                        getPartyAgainFromViewModel();
                        isDataFound = false;
                        System.out.println("===== LAST ITEM OF RECYCLER VIEW ========== ");
                    }
                }
            }
        });
    }

    private void getPartyFromViewModel(){

        LiveData<List<PartyModel>> myPartiesLiveData = viewAllPartyViewModel.getPartiesFromRepository();
        myPartiesLiveData.observe(this, new Observer<List<PartyModel>>() {
            @Override
            public void onChanged(List<PartyModel> partyModels) {

                isDataFound = true;

                if(partyModels == null){
                    System.out.println("=========== NULLABLE ============ ");
                    return;
                } if(partyModels.size() == 0){
                    binding.noPartiesFoundTextView.setVisibility(View.VISIBLE);
                } else {
                    binding.noPartiesFoundTextView.setVisibility(View.GONE);
                }
                notifyAdapter(partyModels);
            }
        });
    }

    private void setPartyAdapter() {
        partyAdapter = new PartyAdapter(this);
        binding.myPartiesRecyclerView.setAdapter(partyAdapter);

        partyAdapter.initItemClickListener(new PartyAdapter.PartyItemClickListener() {
            @Override
            public void onClickItem(int position, PartyModel model) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("party_data", model);
                AppBoiler.navigateToActivity(ViewAllMyPartyActivity.this, PartyDetailActivity.class, bundle);

            }
        });
    }

    private void notifyAdapter(List<PartyModel> partyModelList) {
        partyAdapter.notifyListIsChanged(partyModelList);
    }

    private void getPartyAgainFromViewModel(){

        LiveData<List<PartyModel>> myPartiesLiveData = viewAllPartyViewModel.getPartiesFromRepositoryAgain();
        myPartiesLiveData.observe(this, new Observer<List<PartyModel>>() {
            @Override
            public void onChanged(List<PartyModel> partyModelList) {
                viewAllPartyViewModel.updateMutableListLiveData(partyModelList);
            }
        });
    }

}