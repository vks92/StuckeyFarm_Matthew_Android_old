package com.stuckeyfarm.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTicketPojo implements Parcelable {


 public int id;

    public int   event_id;
    public String   name;
    public String   description;
    public float   price;
    public int    total;
    public int   pending;
    public int   status;
    public int   quntity=0;
    public boolean   isllQuantityShow=false;



    public MyTicketPojo(int id, int event_id, String name, String description, float price, int total, int pending, int status) {
        this.id = id;
        this.event_id = event_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.total = total;
        this.pending = pending;
        this.status = status;
    }


    protected MyTicketPojo(Parcel in) {

        id = in.readInt();
        event_id = in.readInt();
        name = in.readString();
        description = in.readString();
        price = in.readFloat();
        total = in.readInt();
        pending = in.readInt();
        status = in.readInt();
        quntity = in.readInt();
        isllQuantityShow = in.readByte() != 0;

    }

    public static final Creator<MyTicketPojo> CREATOR = new Creator<MyTicketPojo>() {
        @Override
        public MyTicketPojo createFromParcel(Parcel in) {
            return new MyTicketPojo(in);
        }

        @Override
        public MyTicketPojo[] newArray(int size) {
            return new MyTicketPojo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(event_id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeFloat(price);
        parcel.writeInt(total);
        parcel.writeInt(pending);
        parcel.writeInt(status);
        parcel.writeInt(quntity);
        parcel.writeByte((byte) (isllQuantityShow ? 1 : 0));
    }
}
