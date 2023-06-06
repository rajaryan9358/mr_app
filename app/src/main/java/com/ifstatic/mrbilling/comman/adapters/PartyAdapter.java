package com.ifstatic.mrbilling.comman.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.comman.models.PartyModel;

import java.util.ArrayList;
import java.util.List;

public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {

    private Context context;
    private List<PartyModel> partyModelList = new ArrayList<>();
    private PartyItemClickListener partyItemClickListener;

    public PartyAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void notifyListIsChanged(List<PartyModel> partyModelList) {
        this.partyModelList = partyModelList;
        notifyDataSetChanged();
    }

    public void initItemClickListener(PartyItemClickListener partyItemClickListener) {
        this.partyItemClickListener = partyItemClickListener;
    }

    @NonNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PartyViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_my_parties, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PartyViewHolder holder, int position) {

        PartyModel model = partyModelList.get(position);

        holder.partyNameTextView.setText(model.getParty());
        holder.partyAddressTextView.setText(model.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partyItemClickListener.onClickItem(position, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return partyModelList.size();
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder {

        TextView partyNameTextView, partyAddressTextView;
        ImageView dropRightImageView;

        public PartyViewHolder(@NonNull View itemView) {
            super(itemView);

            partyNameTextView = itemView.findViewById(R.id.partyNameTextView);
            partyAddressTextView = itemView.findViewById(R.id.partyAddressTextView);
            dropRightImageView = itemView.findViewById(R.id.dropRightImageView);
        }
    }

    public interface PartyItemClickListener {
        void onClickItem(int position, PartyModel model);
    }
}
