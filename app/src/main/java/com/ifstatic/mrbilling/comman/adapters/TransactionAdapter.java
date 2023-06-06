package com.ifstatic.mrbilling.comman.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.comman.models.TransactionModel;
import com.ifstatic.mrbilling.view.party_detail.PartyDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;
    private List<TransactionModel> transactionModelList = new ArrayList<>();
    private TransactionListClickListener transactionListClickListener;

    public TransactionAdapter(Context context){
        this.context = context;
    }

    public void initItemClickListener(TransactionListClickListener transactionListClickListener){
        this.transactionListClickListener = transactionListClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyListItemChanged(List<TransactionModel> transactionModelList){
        this.transactionModelList = transactionModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_recent_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        TransactionModel model = transactionModelList.get(position);

        if(position%2!=0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemView.setBackgroundColor(context.getColor(R.color.light_color_primary));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.itemView.setBackgroundColor(context.getColor(R.color.white));
            }
        }

        holder.mrNoTextView.setText("#"+model.getMrNo());
        holder.dateAndTimeTextView.setText(model.getDate());
        holder.paymentTextView.setText(model.getPaymentMode());
        holder.amountTextView.setText(model.getAmount());

        /*If adapter is called from home recent transaction detail then hide party name */
        if(!(context instanceof PartyDetailActivity)){
            holder.partyTextView.setText(model.getParty());
        } else {
            holder.partyTextView.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionListClickListener.onClickItem(model,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionModelList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder{

        TextView mrNoTextView , paymentTextView , amountTextView , dateAndTimeTextView , partyTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            mrNoTextView = itemView.findViewById(R.id.mrNoTextView);
            paymentTextView = itemView.findViewById(R.id.paymentTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
            dateAndTimeTextView = itemView.findViewById(R.id.dateAndTimeTextView);
            partyTextView = itemView.findViewById(R.id.partyTextView);
        }
    }

    public interface TransactionListClickListener{
        void onClickItem(TransactionModel model , int position);
    }
}

