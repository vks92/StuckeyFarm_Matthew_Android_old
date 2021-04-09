package com.stuckeyfarm.adapter;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.stuckeyfarm.R;
import com.stuckeyfarm.pojo.MypicModel;
import com.stuckeyfarm.utills.MySharedPreference;

import java.util.ArrayList;


public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<MypicModel>mypicModelArrayList;
    public String bgColor,lbColor,textolor;
    MySharedPreference mySharedPreference;

    public NotificationListAdapter(Context context, ArrayList<MypicModel> mypicModelArrayList) {

        this.context = context;
        this.mypicModelArrayList = mypicModelArrayList;
        mySharedPreference=new MySharedPreference(context);

    }

    @Override
    public NotificationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list, parent, false);
        return new NotificationListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        mySharedPreference.getBgColor();
        bgColor= mySharedPreference.getBgColor();
        lbColor= mySharedPreference.getLableColor();
        textolor= mySharedPreference.getTextColor();

        holder.text_notification.setTextColor( Color.parseColor(textolor));
        holder.relative_top.setBackgroundColor( Color.parseColor(lbColor));
        holder.text_notification.setText(mypicModelArrayList.get(position).getName());

    }

    @Override
    public int getItemCount() {

        return mypicModelArrayList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView text_notification;
        public Switch switch_button;
        RelativeLayout relative_top;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            text_notification=(TextView)itemView.findViewById(R.id.text_notification);
            relative_top=(RelativeLayout)itemView.findViewById(R.id.relative_top);

        }
    }
}
