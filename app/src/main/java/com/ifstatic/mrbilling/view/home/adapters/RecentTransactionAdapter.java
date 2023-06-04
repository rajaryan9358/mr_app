package com.ifstatic.mrbilling.view.home.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.view.home.models.RecentTransactionModel;

import java.util.ArrayList;
import java.util.List;

public class RecentTransactionAdapter extends RecyclerView.Adapter<RecentTransactionAdapter.RecentTransactionViewHolder> {

    private Context context;

    private List<RecentTransactionModel> recentTransactionModelList = new ArrayList<>();

    public RecentTransactionAdapter(Context context){
        this.context = context;
    }

    public void notifyItemChanged(List<RecentTransactionModel> recentTransactionModelList){
        this.recentTransactionModelList = recentTransactionModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecentTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecentTransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_recent_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecentTransactionViewHolder holder, int position) {

        RecentTransactionModel model = recentTransactionModelList.get(position);

        if(position%2!=0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemView.setBackgroundColor(context.getColor(R.color.light_color_primary));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemView.setBackgroundColor(context.getColor(R.color.white));
            }
        }
        holder.partyTextView.setText(model.getParty());
        holder.mrNoTextView.setText("#"+model.getMrNo());
        holder.dateAndTimeTextView.setText(model.getDate());
        holder.paymentTextView.setText(model.getPaymentMode());
        holder.amountTextView.setText(model.getAmount());
    }

    @Override
    public int getItemCount() {
        return recentTransactionModelList.size();
    }

    public class RecentTransactionViewHolder extends RecyclerView.ViewHolder{

        TextView mrNoTextView , paymentTextView , amountTextView , dateAndTimeTextView , partyTextView;
        public RecentTransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            mrNoTextView = itemView.findViewById(R.id.mrNoTextView);
            paymentTextView = itemView.findViewById(R.id.paymentTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            dateAndTimeTextView = itemView.findViewById(R.id.dateAndTimeTextView);
            partyTextView = itemView.findViewById(R.id.partyTextView);
        }
    }
}
