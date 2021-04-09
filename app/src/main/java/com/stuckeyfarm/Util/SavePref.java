package com.stuckeyfarm.Util;

import android.content.Context;
import android.content.SharedPreferences;


public class SavePref {

    public static final String TAG = "SavePref";
    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static SavePref _instance;
    public static final String PREF_TOKEN = "Combisurf";
    public static final String PREF_TOKEN_DATA = "TaxiBookingDriver";

    public SavePref(Context c) {
        context = c;
        preferences = context.getSharedPreferences(PREF_TOKEN, 0);
        editor = preferences.edit();
    }

    public static SavePref getInstance(Context context) {
        if (_instance == null) {
            _instance = new SavePref(context);
        }
        return _instance;
    }
    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String def_value) {
        return preferences.getString(key, def_value);
    }
    public void setDEVICE_TOKEN(String device_token) {
        editor.putString("device_token", device_token);
        editor.commit();
    }

    public String getDEVICE_TOKEN() {
        String device_token = preferences.getString("device_token", "");
        return device_token;
    }

    public void setLocation_id(String Location_id) {
        editor.putString("Location_id", Location_id);
        editor.commit();
    }

    public String getLocation_id() {
        String Location_id = preferences.getString("Location_id", "");
        return Location_id;
    }

    public void setLong(String longitude) {
        editor.putString("longitude", longitude);
        editor.commit();
    }
    public String getLat() {
        String Lat = preferences.getString("Lat", "");
        return Lat;
    }

    public void setLat(String Lat) {
        editor.putString("Lat", Lat);
        editor.commit();
    }

    public String getLong() {
        String longitude = preferences.getString("longitude", "");
        return longitude;
    }
    public void setBoolean(String key, boolean value)
    {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean def_value)
    {

        return preferences.getBoolean(key, def_value);
    }

    public void setEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        String email = preferences.getString("email", "");
        return email;
    }
    public void setCover_pic(String Cover_pic) {
        editor.putString("Cover_pic", Cover_pic);
        editor.commit();
    }

    public String getCover_pic() {
        String Cover_pic = preferences.getString("Cover_pic", "");
        return Cover_pic;
    }
    public void setFirstName(String firstName) {
        editor.putString("firstName", firstName);
        editor.commit();
    }

    public String getFirstName() {
        String firstName = preferences.getString("firstName", "");
        return firstName;
    }

 public void setFrontImage(String frontImage) {
        editor.putString("frontImage", frontImage);
        editor.commit();
    }

    public String getFrontImage() {
        String frontImage = preferences.getString("frontImage", "");
        return frontImage;
    }

    public void setBackImage(String backImage) {
        editor.putString("backImage", backImage);
        editor.commit();
    }

    public String getBackImage() {
        String backImage = preferences.getString("backImage", "");
        return backImage;
    }


    public void setID(String id) {
        editor.putString("id", id);
        editor.commit();
    }

    public String getID() {
        String id = preferences.getString("id", "");
        return id;
    }
    public void setAssignID(String assignID) {
        editor.putString("assignID", assignID);
        editor.commit();
    }

    public String getAssignID() {
        String assignID = preferences.getString("assignID", "");
        return assignID;
    }
    public void setLicences_number(String licences_number) {
        editor.putString("licences_number", licences_number);
        editor.commit();
    }

    public String getLicences_number() {
        String licences_number = preferences.getString("licences_number", "");
        return licences_number;
    }

    public void setDob(String dob) {
        editor.putString("dob", dob);
        editor.commit();
    }

    public String getDob() {
        String dob = preferences.getString("dob", "");
        return dob;
    }

    public void setIssueDate(String issue_date) {
        editor.putString("issue_date", issue_date);
        editor.commit();
    }

    public String getIssueDate() {
        String issue_date = preferences.getString("issue_date", "");
        return issue_date;
    }

    public void setExpireDate(String expire_date) {
        editor.putString("expire_date", expire_date);
        editor.commit();
    }

    public String getExpireDate() {
        String expire_date = preferences.getString("expire_date", "");
        return expire_date;
    }

    public void setnationality(String nationality) {
        editor.putString("nationality", nationality);
        editor.commit();
    }

    public String getnationality() {
        String nationality = preferences.getString("nationality", "");
        return nationality;
    }

    public void setDriver_id(String driver_id) {
        editor.putString("driver_id", driver_id);
        editor.commit();
    }

    public String getDriver_id() {
        String driver_id = preferences.getString("driver_id", "");
        return driver_id;
    }

    public void setName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public String getName() {
        String name = preferences.getString("name", "");
        return name;
    }

    public void setPhone(String phone) {
        editor.putString("phone", phone);
        editor.commit();
    }

    public String getPhone() {
        String phone = preferences.getString("phone", "");
        return phone;
    }
    public void setType(String type) {
        editor.putString("type", type);
        editor.commit();
    }

    public String getType() {
        String type = preferences.getString("type", "");
        return type;
    }

    public void setWeight(String weight) {
        editor.putString("weight", weight);
        editor.commit();
    }

    public String getWeight() {
        String weight = preferences.getString("weight", "");
        return weight;
    }

    public void setHeight(String height) {
        editor.putString("height", height);
        editor.commit();
    }

    public String getHeight() {
        String height = preferences.getString("height", "");
        return height;
    }




    public void setImage(String image) {
        editor.putString("image", image);
        editor.commit();
    }

    public String getImage() {
        String image = preferences.getString("image", "");
        return image;
    }
    public void setTone(String tone) {
        editor.putString("tone", tone);
        editor.commit();
    }

    public String getTone() {
        String tone = preferences.getString("tone", "");
        return tone;
    }
    public void setTtwo(String ttwo){
        editor.putString("ttwo",ttwo);
        editor.commit();
    }
    public String getTtwo(){
        String ttwo=preferences.getString("ttwo","");
        return ttwo;

    }
    public void setTthree(String tthree){
        editor.putString("tthree",tthree);
        editor.commit();
    }
    public String geTtthree(){
        String tthree=preferences.getString("tthree","");
        return tthree;

    }
    public void setTfour(String tfour){
        editor.putString("tfour",tfour);
        editor.commit();
    }
    public String getTfour(){
        String tfour=preferences.getString("tfour","");
        return tfour;

    }
    public void setDriverOnline(String driver_online){
        editor.putString("driver_online",driver_online);
        editor.commit();
    }
    public String getDriverOnline(){
        String driver_online=preferences.getString("driver_online","");
        return driver_online;

    }
    public void setAssigned_Vehicle_Id(String assigned_vehicle_id) {
        editor.putString("assigned_vehicle_id", assigned_vehicle_id);
        editor.commit();
    }

    public String getAssigned_Vehicle_Id() {
        String assigned_vehicle_id = preferences.getString("assigned_vehicle_id", "");
        return assigned_vehicle_id;
    }

    public void setAuthorization_key(String authorization_key) {
        editor.putString("authorization_key", authorization_key);
        editor.commit();
    }
    public void setFirebaseToken(String FirebaseToken) {
        editor.putString("FirebaseToken", FirebaseToken);
        editor.commit();
    }

    public String getFirebaseToken() {
        String FirebaseToken = preferences.getString("FirebaseToken", "");
        return FirebaseToken;
    }
    public String getAuthorization_key() {
        String authorization_key = preferences.getString("authorization_key", "");
        return authorization_key;
    }
    public static void setDeviceToken(Context mContext, String key, String value) {
        SharedPreferences sharedpreferences = mContext.getSharedPreferences(PREF_TOKEN_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDeviceToken(Context mContext, String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(PREF_TOKEN_DATA, Context.MODE_PRIVATE);
        String stringvalue = preferences.getString(key, "");
        return stringvalue;
    }

    public void clearPreferences() {

        preferences.edit().clear().commit();
    }



}
