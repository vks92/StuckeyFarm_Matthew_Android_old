package com.stuckeyfarm.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stuckeyfarm.R;
import com.stuckeyfarm.pojo.Ticket_model;

import java.util.ArrayList;


public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Ticket_model> ticket_modelArrayList;

    public TicketListAdapter(Context context, ArrayList<Ticket_model> ticket_modelArrayList) {
        this.context = context;
        this.ticket_modelArrayList = ticket_modelArrayList;
    }

    @Override
    public TicketListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_ticket, parent, false);
        return new TicketListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.txt_harvest.setText(ticket_modelArrayList.get(position).getTitle());
        holder.first_date.setText(ticket_modelArrayList.get(position).getFirst_date());
        holder.second_date.setText(ticket_modelArrayList.get(position).getLast_date());
        holder.txt_general.setText(ticket_modelArrayList.get(position).getTitle());
        holder.hrwest.setText(ticket_modelArrayList.get(position).getDiscription());
        holder.id.setText("ID"+":"+" "+ticket_modelArrayList.get(position).getUnique_id());
        holder.txt_money.setText("$"+" "+ticket_modelArrayList.get(position).getPrice());
        Glide.with(context).load("https://farmmarketapp.com/stuckey/public/"+ticket_modelArrayList.get(position).getBarcodeImage()).into(holder.img);


    }

    @Override
    public int getItemCount() {

        return ticket_modelArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView txt_harvest,first_date,second_date,txt_general,hrwest,id,txt_money;
        ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_harvest=itemView.findViewById(R.id.txt_harvest);
            first_date=itemView.findViewById(R.id.first_date);
            second_date=itemView.findViewById(R.id.second_date);
            txt_general=itemView.findViewById(R.id.txt_general);
            hrwest=itemView.findViewById(R.id.hrwest);
            id=itemView.findViewById(R.id.id);
            txt_money=itemView.findViewById(R.id.txt_money);
            img=itemView.findViewById(R.id.img);


        }
    }
}
