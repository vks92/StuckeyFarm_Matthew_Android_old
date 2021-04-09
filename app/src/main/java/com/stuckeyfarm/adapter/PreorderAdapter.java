package com.stuckeyfarm.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stuckeyfarm.R;
import com.stuckeyfarm.pojo.Preorder_model;
import com.stuckeyfarm.utills.MySharedPreference;

import java.util.ArrayList;


public class PreorderAdapter extends RecyclerView.Adapter<PreorderAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<Preorder_model> preorder_modelArrayList;
    public String bgColor,lbColor,textolor;
    MySharedPreference mySharedPreference;

    public PreorderAdapter(Context context, ArrayList<Preorder_model> preorder_modelArrayList) {

        this.context = context;
        this.preorder_modelArrayList = preorder_modelArrayList;
        mySharedPreference=new MySharedPreference(context);

    }

    @Override
    public PreorderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_pre_oder_item, parent, false);
        return new PreorderAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        mySharedPreference.getBgColor();
        bgColor= mySharedPreference.getBgColor();
        lbColor= mySharedPreference.getLableColor();
        textolor= mySharedPreference.getTextColor();

        holder.txt_title.setTextColor( Color.parseColor(textolor));
        holder.txt_name.setTextColor( Color.parseColor(textolor));
        holder.txt_description.setTextColor( Color.parseColor(textolor));
        holder.bt_buy.setBackgroundColor( Color.parseColor(lbColor));
        holder.root_ll.setBackgroundColor( Color.parseColor(bgColor));
        holder.txt_title.setText(preorder_modelArrayList.get(position).getTitle());
        holder.txt_name.setText(preorder_modelArrayList.get(position).getName());
        holder.txt_description.setText(preorder_modelArrayList.get(position).getDescription());

        Glide.with(context).load(
                "https://farmmarketapp.com/stuckey/public/images/pre_order_product/"+preorder_modelArrayList.get(position).getImage()).into(holder.img);

    }

    @Override
    public int getItemCount() {

        return preorder_modelArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,txt_name,txt_description;
        ImageView img;
        Button bt_buy;
        LinearLayout root_ll;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_name=itemView.findViewById(R.id.txt_name);
            txt_description=itemView.findViewById(R.id.txt_description);
            img=itemView.findViewById(R.id.img);
            bt_buy=itemView.findViewById(R.id.bt_buy);
            root_ll=itemView.findViewById(R.id.root_ll);

        }
    }
}
