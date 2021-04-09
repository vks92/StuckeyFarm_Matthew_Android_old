package com.stuckeyfarm.utills;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ron Guleria on 21/2/18.
 */

public class MySharedPreference {

    public Context activity;
    private SharedPreferences sharedPreferences;
    public final static String BG_COLOR="bg_color";
    public final static String TEXT_COLOR="text_color";
    public final static String LABLE_COLOR="item_bg_color";

    public  final static  String FARM_MAP_TXT="farm_map_txt";
    public  final static  String FARM_MAP_IMG="farm_map_img";

    public  final static  String APPLE_PICK_TXT="apple_pick_txt";
    public  final static  String APPLE_PICK_IMG="apple_pick_img";

    public  final static  String FASTIVAL_TXT="fastival_txt";
    public  final static  String FASTIVAL_IMG="fastival_img";

    public  final static  String BUY_TXT="buy_txt";
    public  final static  String BUY_IMG="buy_img";

    public  final static  String ABOUT_TXT="about_txt";
    public  final static  String ABOUT_IMG="about_img";


    public  final static  String IMEI_NO="imei_number";



    public MySharedPreference(Context activity) {
        this.activity = activity;
        createSharedPreferences();
    }

    public void createSharedPreferences() {
        String PACKAGE_NAME = activity.getApplicationContext().getPackageName();;
        sharedPreferences = activity.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();

    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();

    }public int getInt(String key,int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }



    public boolean getboolean(String key,boolean defValue){
        return sharedPreferences.getBoolean(key,defValue);
    }

    public void putboolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();

    }

    public void removeString(String key) {
        sharedPreferences.edit().remove(key).commit();
    }

    public void removeAll() {
        sharedPreferences.edit().clear().commit();
    }



    public void setBoolean(String key,boolean value){
        sharedPreferences.edit().putBoolean(key, value).commit();

    }
    public boolean isBoolean(String key){
        return sharedPreferences.getBoolean(key,false);
    }


    public String getBgColor(){
        return sharedPreferences.getString(BG_COLOR, "#000000");
    }
    public void setBgColor( String value) {
        sharedPreferences.edit().putString(BG_COLOR, value).commit();

    }


    public String getIMEI_NO(){
        return sharedPreferences.getString(IMEI_NO, "-1");
    }
    public void setIMEI_NO( String value) {
        sharedPreferences.edit().putString(IMEI_NO, value).commit();

    }


    public String getTextColor(){
        return sharedPreferences.getString(TEXT_COLOR, "#000000");
    }
    public void setTextColor( String value) {
        sharedPreferences.edit().putString(TEXT_COLOR, value).commit();

    }

    public String getLableColor(){
        return sharedPreferences.getString(LABLE_COLOR, "#000000");
    }
    public void setLableColor( String value) {
        sharedPreferences.edit().putString(LABLE_COLOR, value).commit();

    }




}
