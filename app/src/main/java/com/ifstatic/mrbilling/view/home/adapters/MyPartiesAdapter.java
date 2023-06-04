package com.ifstatic.mrbilling.view.home.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ifstatic.mrbilling.R;
import com.ifstatic.mrbilling.view.home.models.MyPartiesModel;

import java.util.ArrayList;
import java.util.List;

public class MyPartiesAdapter extends RecyclerView.Adapter<MyPartiesAdapter.MyPartiesViewHolder> {

    private Context context;
    private List<MyPartiesModel> myPartiesModelList = new ArrayList<>();
    private ItemClickListener itemClickListener;

    public MyPartiesAdapter(Context context){
        this.context = context;
    }

    public void notifyListIsChanged(List<MyPartiesModel> myPartiesModelList){
        this.myPartiesModelList = myPartiesModelList;
        notifyDataSetChanged();
    }

    public void initClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyPartiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyPartiesViewHolder(LayoutInflater.from(context).inflate(R.layout.listitem_my_parties,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPartiesViewHolder holder, int position) {

        MyPartiesModel model = myPartiesModelList.get(position);

        holder.partyNameTextView.setText(model.getParty());
        holder.partyAddressTextView.setText(model.getAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myPartiesModelList.size();
    }

    public class MyPartiesViewHolder extends RecyclerView.ViewHolder{

        TextView partyNameTextView , partyAddressTextView;
        ImageView dropRightImageView;
        public MyPartiesViewHolder(@NonNull View itemView) {
            super(itemView);

            partyNameTextView = itemView.findViewById(R.id.partyNameTextView);
            partyAddressTextView = itemView.findViewById(R.id.partyAddressTextView);
            dropRightImageView = itemView.findViewById(R.id.dropRightImageView);
        }
    }

    public interface ItemClickListener {
        void onClickItem(int position);
    }
}
