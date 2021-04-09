package com.stuckeyfarm.ui.fragment


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
import com.stuckeyfarm.ui.activity.HomeActivity
import com.stuckeyfarm.utills.MyProgressBar
import com.stuckeyfarm.utills.MySharedPreference
import com.stuckeyfarm.utills.MySharedPreference.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONObject


class HomeFragment : Fragment(), View.OnClickListener {

    var rootView: View? = null
    var mySharedPreference: MySharedPreference? = null
    var myProgressBar: MyProgressBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        init()
        appTheme()
        setUi()
        setListener()
        return rootView

    }

    private fun init() {

        mySharedPreference = MySharedPreference(activity)
        myProgressBar = MyProgressBar(activity)


    }

    private fun setListener() {

        rootView!!.cv_apple_pick.setOnClickListener(this)
        rootView!!.cv_about_us.setOnClickListener(this)
        rootView!!.cv_event_info.setOnClickListener(this)
        rootView!!.preorder.setOnClickListener(this)
        rootView!!.cv_farm_map.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {

            rootView!!.cv_farm_map.id -> {

                (activity as HomeActivity).replaceFragment(LocationFragment(), "LocationFragment")

             }

            rootView!!.cv_apple_pick.id -> {

                (activity as HomeActivity).replaceFragment(ApplePickFragment(), "ApplePickFragment")

            }
            rootView!!.cv_about_us.id -> {

                (activity as HomeActivity).replaceFragment(AboutUsFragment(), "AboutUsFragment")
            }

            rootView!!.cv_event_info.id -> {

                (activity as HomeActivity).replaceFragment(EventInfoFragment(), "EventInfoFragment")
            }

            rootView!!.cv_event_info.id -> {

                (activity as HomeActivity).replaceFragment(SettingFragment(), "SettingFragment")
            }
            rootView!!.preorder.id -> {

                (activity as HomeActivity).replaceFragment(PreorderFragment(), "SettingFragment")
            }
        }
    }

    fun appTheme() {

        myProgressBar!!.show()
        AndroidNetworking.get(API.GET_THEME_COLOR)
           // .addQueryParameter("location_id", mySharedPreference!!.getString("str_location_id"))
            .setTag(this)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    if (response.getBoolean("status")) {

                        if(!response.getString("response").equals("null")) {

                            var res = response.getJSONObject("response")

                            if (res != null) {

                                mySharedPreference!!.setBgColor(res.getString("background_color"))
                                mySharedPreference!!.setTextColor(res.getString("text_color"))
                                mySharedPreference!!.setLableColor(res.getString("lable_color"))

                                (activity as HomeActivity).setUi()

                            }
                        }
                    }

                    appData()

                }

                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()

                    appData()

                }
         })

    }

    fun appData() {

        AndroidNetworking.get(API.GET_DASHBOARD)
            //.addQueryParameter("location_id", mySharedPreference!!.getString("str_location_id"))
            .setTag(this)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    myProgressBar!!.dismiss()
                    if (response.getBoolean("status")) {
                        if(!response.getString("response").equals("null")) {

                            var res = response.getJSONArray("response")

                            if (res != null) {

                                for (i in 0..(res.length() - 1)) {

                                    var item = res.getJSONObject(i)

                                    when (i) {

                                        0 -> {
                                            mySharedPreference!!.putString(FARM_MAP_TXT, item.getString("lable"))
                                            mySharedPreference!!.putString(
                                                FARM_MAP_IMG,
                                                item.getString("dashboardImage")
                                            )
                                        }

                                        1 -> {

                                            mySharedPreference!!.putString(APPLE_PICK_TXT, item.getString("lable"))
                                            mySharedPreference!!.putString(
                                                APPLE_PICK_IMG,
                                                item.getString("dashboardImage")
                                            )

                                        }

                                        2 -> {
                                            mySharedPreference!!.putString(FASTIVAL_TXT, item.getString("lable"))
                                            mySharedPreference!!.putString(
                                                FASTIVAL_IMG,
                                                item.getString("dashboardImage")
                                            )
                                        }

                                        3 -> {
                                            mySharedPreference!!.putString(BUY_TXT, item.getString("lable"))
                                            mySharedPreference!!.putString(BUY_IMG, item.getString("dashboardImage"))
                                        }

                                4->{
                                    mySharedPreference!!.putString(ABOUT_TXT, item.getString("lable"))
                                    mySharedPreference!!.putString(ABOUT_IMG, item.getString("dashboardImage"))
                                }
                                    }
                                }



                            }
                        }
                    }
                    setUi()
                }

                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()
                }
            })
    }

    fun setUi() {

        var bgColor = mySharedPreference!!.getBgColor()
        var textolor = mySharedPreference!!.getTextColor()

        /*  img_fram
          img_apple_pick
          img_festival
          img_buy
          img_about_us
  */

        rootView!!.txt_farm.setTextColor(Color.parseColor(textolor))
        rootView!!.txt_apple_pick_list.setTextColor(Color.parseColor(textolor))
        rootView!!.txt_festival.setTextColor(Color.parseColor(textolor))
        rootView!!.txt_buy.setTextColor(Color.parseColor(textolor))
        rootView!!.txt_about.setTextColor(Color.parseColor(textolor))

        if (!mySharedPreference!!.getString(FARM_MAP_TXT).equals("")) {

            rootView!!.txt_farm.setText(mySharedPreference!!.getString(FARM_MAP_TXT))

        }

        if (!mySharedPreference!!.getString(APPLE_PICK_TXT).equals("")) {

           rootView!!.txt_apple_pick_list.setText(mySharedPreference!!.getString(APPLE_PICK_TXT))

        }

        if (!mySharedPreference!!.getString(FASTIVAL_TXT).equals("")) {

            rootView!!.txt_festival.setText(mySharedPreference!!.getString(FASTIVAL_TXT))

        }

        if (!mySharedPreference!!.getString(BUY_TXT).equals("")) {

            rootView!!.txt_buy.setText(mySharedPreference!!.getString(BUY_TXT))

        }

        if (!mySharedPreference!!.getString(ABOUT_TXT).equals("")) {

            rootView!!.txt_about.setText(mySharedPreference!!.getString(ABOUT_TXT))

        }

        val farnimgurl = API.DashBoardImgUrl + mySharedPreference!!.getString(MySharedPreference.FARM_MAP_IMG)
        val appleimgurl = API.DashBoardImgUrl + mySharedPreference!!.getString(MySharedPreference.APPLE_PICK_IMG)
        val fastivalimgurl = API.DashBoardImgUrl + mySharedPreference!!.getString(MySharedPreference.FASTIVAL_IMG)
        val buyimgurl = API.DashBoardImgUrl + mySharedPreference!!.getString(MySharedPreference.BUY_IMG)
        val aboutimgurl = API.DashBoardImgUrl + mySharedPreference!!.getString(MySharedPreference.ABOUT_IMG)

        Picasso.get().load(farnimgurl).error(R.drawable.bg_apples).placeholder(R.drawable.bg_apples)
            .into(rootView!!.img_fram);
        Picasso.get().load(appleimgurl).error(R.drawable.bg_apples).placeholder(R.drawable.bg_apples)
            .into(rootView!!.img_apple_pick);
        Picasso.get().load(fastivalimgurl).error(R.drawable.bg_festive_info).placeholder(R.drawable.bg_festive_info)
            .into(rootView!!.img_festival);
        Picasso.get().load(buyimgurl).error(R.drawable.bg_donuts).placeholder(R.drawable.bg_donuts)
            .into(rootView!!.img_buy);
        Picasso.get().load(aboutimgurl).error(R.drawable.bg_about_us).placeholder(R.drawable.bg_about_us)
            .into(rootView!!.img_about_us);

    }
 }
