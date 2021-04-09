package com.stuckeyfarm.ui.fragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.squareup.picasso.Picasso

import com.stuckeyfarm.R
import com.stuckeyfarm.api.API
import com.stuckeyfarm.ui.activity.BuyTicketActivity
import com.stuckeyfarm.ui.activity.HomeActivity
import com.stuckeyfarm.ui.activity.TicketListActivity
import com.stuckeyfarm.utills.MyProgressBar
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.fragment_event_info.*
import kotlinx.android.synthetic.main.fragment_event_info.view.*
import kotlinx.android.synthetic.main.item_events.view.*
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class EventInfoFragment : Fragment(), View.OnClickListener {

    var mySharedPreference: MySharedPreference? = null
    var myProgressBar: MyProgressBar? = null
    var rootView: View? = null
    var bgImg = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_event_info, container, false)
        init()
        setListener()
        showData()
        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView!!.ll_fragment_root.setBackgroundColor(Color.parseColor(mySharedPreference!!.bgColor))
    }

    private fun init() {

        mySharedPreference = MySharedPreference(activity)
        myProgressBar = MyProgressBar(activity)


    }

    private fun setListener() {

        rootView!!.imgBack.setOnClickListener(this)
        rootView!!.imgTicket.setOnClickListener(this)

    }

    fun showdemoList() {
        for (i in 0..30) {
            var view = layoutInflater.inflate(R.layout.item_events, null)
            view.setOnClickListener(View.OnClickListener {
                (activity as HomeActivity).replaceFragment(
                    AppleFullInfoFragment(),
                    "AppleFullInfoFragment"
                )
            })

            rootView!!.ll_show_List.addView(view)
        }
    }



    fun showData() {
        myProgressBar!!.show()
        AndroidNetworking.get(API.GET_EVENT)
            // .addQueryParameter("location_id",mySharedPreference!!.getString("str_location_id"))
            .setTag("GET_EVENT")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    if (response.getBoolean("status")) {
                        var res = response.getJSONArray("response")
                        bgImg = response.getString("image")

                        for (i in 0..(res.length() - 1)) {
                            try {
                                var item = res.getJSONObject(i)
                                item.getString("name")
                                item.getString("title")
                                item.getString("description")
                                item.getString("image")
                                item.getString("date_to")
                                item.getString("date_from")

                                var view = layoutInflater.inflate(R.layout.item_events, null)
                                view.txt_title.text = item.getString("title")
                                view.txt_title.setTextColor(Color.parseColor(mySharedPreference!!.textColor))
                                var date = getDate(item.getString("date_to")) + "\nto\n" + getDate(
                                    item.getString("date_from")
                                )

                                view.txt_date.text = date
                                view.txt_date.setTextColor(Color.parseColor(mySharedPreference!!.textColor))
                                view.txt_name.text = item.getString("name")
                                view.txt_name.setTextColor(Color.parseColor(mySharedPreference!!.textColor))
                                view.txt_description.text = item.getString("description")
                                view.txt_description.setTextColor(
                                    Color.parseColor(
                                        mySharedPreference!!.textColor
                                    )
                                )
                                view.bt_buy.setBackgroundColor(Color.parseColor(mySharedPreference!!.lableColor))
                                view.bt_buy.setTextColor(Color.parseColor(mySharedPreference!!.textColor))

                                view.bt_buy.setOnClickListener {
                                    startActivity(
                                        Intent(
                                            activity,
                                            BuyTicketActivity::class.java
                                        ).putExtra("bgImg", bgImg)
                                            .putExtra("event_id", item.getString("id"))
                                    )
                                }
                                rootView!!.ll_show_List.addView(view)

                            } catch (e: Exception) {

                            }

                        }

                        myProgressBar!!.dismiss()

                        if (bgImg != "") {

                            Picasso.get().load(bgImg).into(img_full_screen)

                        }
                    }
                }

                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()

                }
            })

    }

    private fun getDate(ourDate: String): String {

        var ourDate = ourDate
        try {
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value = formatter.parse(ourDate)

            val dateFormatter = SimpleDateFormat("MMM.dd,yyyy-hh:mm a") //this format changeable

            ourDate = dateFormatter.format(value)

            //Log.d("ourDate", ourDate);
        } catch (e: Exception) {
            ourDate = ourDate
        }

        return ourDate
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.imgBack-> {
                activity?.onBackPressed()
            }

            R.id.imgTicket-> {
                val intent = Intent(getActivity(), TicketListActivity::class.java)
                // .putExtra("event_id",str_id)
                activity?.startActivity(intent)

            }
        }
    }
}