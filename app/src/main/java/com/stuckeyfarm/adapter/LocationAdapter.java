package com.stuckeyfarm.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.stuckeyfarm.R;
import com.stuckeyfarm.pojo.Data;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Data> dataArrayList;
    private int lastSelectedPosition = -1;
    SetOnitemClickListener setOnitemClickListener;

   public interface  SetOnitemClickListener{

       public void onItemClick(int position);

    }

    public LocationAdapter(Context context, ArrayList<Data> dataArrayList,SetOnitemClickListener setOnitemClickListener) {
        this.context = context;
        this.dataArrayList = dataArrayList;
        this.setOnitemClickListener = setOnitemClickListener;
    }

    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_location, parent, false);

        return new LocationAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.text_location_cu.setText(dataArrayList.get(position).getLocation_name());
        holder.radio_button.setChecked(lastSelectedPosition == position);

    }

    @Override
    public int getItemCount() {

        return dataArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text_location_cu;
        public RadioButton radio_button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_location_cu = (TextView) itemView.findViewById(R.id.text_location_cu);
            radio_button = (RadioButton) itemView.findViewById(R.id.radio_button);
            radio_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lastSelectedPosition = getAdapterPosition();
                    setOnitemClickListener.onItemClick(getPosition());
                    notifyDataSetChanged();
                }
            });
        }

    }
}
