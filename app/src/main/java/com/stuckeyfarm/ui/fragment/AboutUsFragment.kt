package com.stuckeyfarm.ui.fragment


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.squareup.picasso.Picasso
import com.stuckeyfarm.R
import com.stuckeyfarm.api.API
import com.stuckeyfarm.utills.MyProgressBar
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.dialog_open_close.*
import kotlinx.android.synthetic.main.fragment_about_us.*
import kotlinx.android.synthetic.main.fragment_about_us.view.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class AboutUsFragment : Fragment() , View.OnClickListener{

    var email=""
    var phone=""
    var des=""
    var image=""

    var mon=""
    var tue=""
    var wed=""
    var thu=""
    var fri=""
    var sat=""
    var sun=""


 var rootView: View? =null
    var myProgressBar: MyProgressBar?=null
    var mySharedPreference: MySharedPreference? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_about_us, container, false)

        rootView!!.ll_show_List.visibility=View.GONE
        init()
        appData()
        setlistener()
        return rootView

    }
    private fun init() {

        mySharedPreference= MySharedPreference(activity)
        myProgressBar= MyProgressBar(activity)

        var bgColor= mySharedPreference!!.getBgColor()
        rootView!!. ll_root.setBackgroundColor(Color.parseColor(bgColor))

    }

    private fun setlistener() {
        rootView!!.ll_back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            rootView!!.ll_back.id ->{ activity?.onBackPressed()}
        }
    }

    fun appData(){

        myProgressBar!!.show()
        AndroidNetworking.get(API.GET_ABOUT_US)
         //   .addQueryParameter("location_id",mySharedPreference!!.getString("str_location_id"))
            .setTag(this)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if(response.getBoolean("status")) {

                        if (!response.getString("response").equals("null")) {

                            var res = response.getJSONObject("response")

                            if (res != null) {

                                des = res.getString("description")
                                image = res.getString("image")
                                email = res.getString("email")
                                phone = res.getString("phone")


                                res.getString("mon_opening_time")
                                res.getString("mon_closing_time")
                                res.getString("tus_opening_time")
                                res.getString("tus_closeing_time")
                                res.getString("wed_opening_time")
                                res.getString("wed_closeing_time")
                                res.getString("thur_opening_time")
                                res.getString("thur_closeing_time")
                                res.getString("fri_opening_time")
                                res.getString("fri_closeing_time")
                                res.getString("sat_opening_time")
                                res.getString("sat_closeing_time")
                                res.getString("sun_opening_time")
                                res.getString("sun_closeing_time")

                                mon =
                                    getDate(res.getString("mon_opening_time")) + " - " + getDate(res.getString("mon_closing_time"))
                                tue =
                                    getDate (res.getString("tus_opening_time")) + " - " + getDate(res.getString("tus_closeing_time"))
                                wed =
                                    getDate (res.getString("wed_opening_time")) + " - " + getDate(res.getString("wed_closeing_time"))
                                thu =
                                    getDate  (res.getString("thur_opening_time")) + " - " + getDate(res.getString("thur_closeing_time"))
                                fri =
                                    getDate (res.getString("fri_opening_time")) + " - " +  getDate(res.getString("fri_closeing_time"))
                                sat =
                                    getDate  (res.getString("sat_opening_time")) + " - " + getDate (res.getString("sat_closeing_time"))
                                sun =
                                    getDate (res.getString("sun_opening_time")) + " - " + getDate(res.getString("sun_closeing_time"))

                            }
                        }
                    }
                    setUi()
                    OpenCloseTime()
                }
                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()
                }
            })


    }


    fun setUi(){

        var lbColor= mySharedPreference!!.getLableColor()
        var textolor= mySharedPreference!!.getTextColor()
        var imgurl=API.AboutUsImageUrl+image;

        Picasso.get()
            .load(imgurl)
            .into(rootView!!.img_about);

        rootView!!. txt_label.setTextColor( Color.parseColor(lbColor))
        rootView!!. txt_phone.setTextColor( Color.parseColor(textolor))
        rootView!!. txt_email.setTextColor( Color.parseColor(textolor))
       // rootView!!. txt_label.setBackgroundColor( Color.parseColor(lbColor))
        rootView!!. ll_email_us.setBackgroundColor( Color.parseColor(lbColor))
        rootView!!. ll_call_us.setBackgroundColor( Color.parseColor(lbColor))
        rootView!!. txt_des.setText(des)

        var bgColor= mySharedPreference!!.getBgColor()
        rootView!!. ll_root.setBackgroundColor(Color.parseColor(bgColor))

        rootView!!.ll_email_us.setOnClickListener(View.OnClickListener {

            val emailUrl = "mailto:"+email+"?subject=Subject Text&body=Body Text"
            val request = Intent(Intent.ACTION_VIEW)
            request.data = Uri.parse(emailUrl)
            startActivity(request)

        })

        rootView!!. ll_call_us.setOnClickListener(View.OnClickListener {

            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
           activity!!. startActivity(intent)

        })

        myProgressBar!!.dismiss()

        rootView!!.ll_show_List.visibility=View.VISIBLE

    }

    fun OpenCloseTime(){

        open_close_time.setText("Hours : Open - Close  $mon")
        open_close_time.setOnClickListener{
            var dialog = Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_open_close)
            dialog.txt_1.text=mon
            dialog.txt_2.text=tue
            dialog.txt_3.text=wed
            dialog.txt_4.text=thu
            dialog.txt_5.text=fri
            dialog.txt_6.text=sat
            dialog.txt_7.text=sun

            dialog.txt_close.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()

        }
    }

    private fun getDate(ourDate: String): String {

        var ourDate = ourDate
        try {
            val formatter = SimpleDateFormat("HH:mm:ss")
            formatter.timeZone = TimeZone.getDefault()
            val value = formatter.parse(ourDate)

            val dateFormatter = SimpleDateFormat("hh:mm a") //this format changeable

            ourDate = dateFormatter.format(value)

            //Log.d("ourDate", ourDate);
        } catch (e: Exception) {
            ourDate = ourDate
        }

        return ourDate
    }
}
