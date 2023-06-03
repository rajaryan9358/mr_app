package com.ifstatic.mrbilling.view.home.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.RecentTransactionViewHolder> {

    private Context context;

    public RecentTransactionAdapter(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public RecentTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTransactionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecentTransactionViewHolder extends RecyclerView.ViewHolder{

        public RecentTransactionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
