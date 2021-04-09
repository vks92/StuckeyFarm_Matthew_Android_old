package com.stuckeyfarm.adapter;

import android.app.Activity;
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
import com.stuckeyfarm.R;
import com.stuckeyfarm.pojo.MyTicketPojo;
import com.stuckeyfarm.utills.MySharedPreference;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.String.format;

public class MyTicketAdapter  extends RecyclerView.Adapter<MyTicketAdapter.ViewHolder>{

    Activity activity;
    ArrayList<MyTicketPojo> arrayList;
    float TotalPrice=0.00f;
    int TotalQuntity=0;

    String bt_bg="#000000";
    String bt_text="#FFFFFF";
    AddTicketValue addTicketValue;

    public MyTicketAdapter(Activity activity, ArrayList<MyTicketPojo> arrayList,AddTicketValue addTicketValue) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.addTicketValue = addTicketValue;
        MySharedPreference mySharedPreference=new MySharedPreference(activity);
        bt_bg=mySharedPreference.getLableColor();
        bt_text=mySharedPreference.getTextColor();

    }

   public interface AddTicketValue{

        public void ticketValue(int TotalQuntity,float TotalPrice,int position);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        View listItem= layoutInflater.inflate(R.layout.item_ticket, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,  final int postion) {

        MyTicketPojo myTicketPojo=arrayList.get(postion);

        holder.bt_buy.setBackgroundColor(Color.parseColor(bt_bg));
        holder.bt_buy.setTextColor(Color.parseColor(bt_text));

        holder.txt_title.setText(myTicketPojo.name);
        holder.txt_description.setText(myTicketPojo.description);

        float fr=myTicketPojo.price;

      //  holder.txt_price.setText("$"+myTicketPojo.price);

        DecimalFormat form = new DecimalFormat("0.00");
        holder.txt_price.setText("$"+form.format(fr));

        if(myTicketPojo.isllQuantityShow){

            holder.ll_quantity.setVisibility(View.VISIBLE);
            holder.bt_buy.setVisibility(View.GONE);

        } else {

            holder.ll_quantity.setVisibility(View.GONE);
            holder.bt_buy.setVisibility(View.VISIBLE);

        }

        holder.bt_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.ll_quantity.setVisibility(View.VISIBLE);
                holder.bt_buy.setVisibility(View.GONE);
                arrayList.get(postion).isllQuantityShow=true;

                if(arrayList.get(postion).quntity==0){

                    arrayList.get(postion).quntity=1;
                    holder.txt_quantity.setText( arrayList.get(postion).quntity+"");

                    TotalQuntity=TotalQuntity+1;
                    TotalPrice=TotalPrice + arrayList.get(postion).price;
                    addTicketValue.ticketValue(TotalQuntity,TotalPrice,postion);

                }
            }
        });

        holder.img_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TotalQuntity=TotalQuntity-1;
                TotalPrice=TotalPrice - arrayList.get(postion).price;
                addTicketValue.ticketValue(TotalQuntity,TotalPrice,postion);

                arrayList.get(postion).quntity=arrayList.get(postion).quntity-1;

                if(arrayList.get(postion).quntity==0) {

                    arrayList.get(postion).quntity=0;
                    holder.ll_quantity.setVisibility(View.GONE);
                    holder.bt_buy.setVisibility(View.VISIBLE);
                    arrayList.get(postion).isllQuantityShow = false;
                    return;

                }

                if(arrayList.get(postion).quntity>0){


                    holder.txt_quantity.setText( arrayList.get(postion).quntity+"");

                }


            }
        });

        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TotalQuntity=TotalQuntity+1;
                TotalPrice=TotalPrice +  arrayList.get(postion).price;
                addTicketValue.ticketValue(TotalQuntity,TotalPrice,postion);
                arrayList.get(postion).quntity=arrayList.get(postion).quntity+1;

                holder.txt_quantity.setText( arrayList.get(postion).quntity+"");

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_title,txt_description,txt_price,txt_quantity;
        LinearLayout ll_quantity;
        Button bt_buy;
        ImageView img_less,img_add;

        public ViewHolder(View itemView) {
            super(itemView);

            txt_quantity=itemView.findViewById(R.id.txt_quantity);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_description=itemView.findViewById(R.id.txt_description);
            txt_price=itemView.findViewById(R.id.txt_price);
            ll_quantity=itemView.findViewById(R.id.ll_quantity);
            bt_buy=itemView.findViewById(R.id.bt_buy);
            img_less=itemView.findViewById(R.id.img_less);
            img_add=itemView.findViewById(R.id.img_add);


        }
    }

    public ArrayList<MyTicketPojo> getList(){

        return arrayList;

    }
}