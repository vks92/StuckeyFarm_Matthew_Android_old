package com.stuckeyfarm.ui.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle

import android.text.TextUtils
import android.util.Patterns
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.squareup.picasso.Picasso
import com.stuckeyfarm.R
import com.stuckeyfarm.adapter.MyTicketAdapter
import com.stuckeyfarm.api.API
import com.stuckeyfarm.pojo.MyTicketPojo
import com.stuckeyfarm.utills.MyAlert
import com.stuckeyfarm.utills.MyProgressBar
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.activity_buy_ticket.*
import kotlinx.android.synthetic.main.dialog_confirm_ticket.*
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*


class BuyTicketActivity : AppCompatActivity() {

    var eventId="-1"
    lateinit var mySharedPreference: MySharedPreference
    lateinit var myProgressBar : MyProgressBar
    lateinit var myTicketAdapter: MyTicketAdapter
    var arrayList: ArrayList<MyTicketPojo> = ArrayList()
    var TotalQuntity=0
    var selectedPosition=-1
    var TotalPrice=0.00f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_buy_ticket)
        init()
        setRecycleView()
        showData()

    }

    fun init(){

        mySharedPreference=MySharedPreference(this@BuyTicketActivity)
        myProgressBar= MyProgressBar(this@BuyTicketActivity)
        ll_root.setBackgroundColor(Color.parseColor(mySharedPreference.bgColor))
        linaer_ll.setBackgroundColor(Color.parseColor(mySharedPreference.bgColor))

        eventId =intent.getStringExtra("event_id")

        var bgImg =intent.getStringExtra("bgImg")

        if(bgImg!=null) {

            if (bgImg != "") {

                Picasso.get().load(bgImg).into(img_bg)
            }
        }

        img_back.setOnClickListener {

            finish()

        }

        txt_buy.setOnClickListener {

            buyTicket()

        }
    }

    private fun buyTicket() {

        //  holder.txt_price.setText("$"+myTicketPojo.price);
        val form = DecimalFormat("0.00")
        var tmpArrayList: ArrayList<MyTicketPojo> = myTicketAdapter.getList()

        if(tmpArrayList!=null){

            if(TotalQuntity==0){

                MyAlert().show(this@BuyTicketActivity,"Alert","Please Add tickets to buy")

                return

            }

            var dialog = Dialog(this@BuyTicketActivity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_confirm_ticket)
            dialog.txt_dialog_total_quantity.text= "Total Quantity: "+TotalQuntity;
            dialog.txt_dialog_total_price.text= "Total Price: $"+form.format(TotalPrice.toDouble());

               dialog.bt_pay.setBackgroundColor(Color.parseColor(mySharedPreference.lableColor))
               dialog.bt_pay.setTextColor(Color.parseColor(mySharedPreference.textColor))

              dialog.img_close.setOnClickListener {

                dialog.dismiss()

            }

            dialog.bt_pay.setOnClickListener {

                if(!isValidEmail(dialog.etEmail.text.toString())){

                    MyAlert().show(this@BuyTicketActivity,"Alert","Please Enter Valid Email Address.")

                } else{

                    val intent = Intent(this@BuyTicketActivity, PaymentActivity::class.java)
                    intent.putExtra("totalPrice", TotalPrice.toString())
                    intent.putExtra("totalQuantity", TotalQuntity)
                    intent.putParcelableArrayListExtra("list",arrayList)
                    intent.putExtra("position",selectedPosition)
                    intent.putExtra("email", dialog.etEmail.text.toString())
                    startActivity(intent)

                    dialog.dismiss()

                }
            }

            dialog.show()

        }
    }

    fun showData(){

        myProgressBar.show()

        AndroidNetworking.get(API.GET_TICKET)
           // .addQueryParameter("location_id",mySharedPreference!!.getString("str_location_id"))
            .setTag("GET_TICKET")
            .setPriority(Priority.LOW)
            .addQueryParameter("event_id",eventId)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    myProgressBar.dismiss()
                    if(response.getBoolean("status")){

                        val bgImg =response.getString("image")

                        if (bgImg != "") {

                            Picasso.get().load(bgImg).into(img_bg)

                        }

                        val ticketArry=response.getJSONArray("response");

                        for(i in 0..(ticketArry.length()-1)) {
                            val itemJson= ticketArry.getJSONObject(i);

                            val id = itemJson.getInt("id")
                            val event_id = itemJson.getInt("event_id")
                            val name =  itemJson.getString("name")
                            var description =   itemJson.getString("description")
                            var price =   itemJson.getString("price")
                            var total =   itemJson.getInt("total")
                            var pending =   itemJson.getInt("pending")
                            var status =   itemJson.getInt("status")

                            var event_info=  itemJson.getJSONObject("event_info")

                            var event_title=event_info.getString("title")

                            var myTicketPojo : MyTicketPojo = MyTicketPojo(id,event_id,name,event_title,price.toFloat(),total,pending,status)

                            arrayList.add(myTicketPojo)

                        }

                        myTicketAdapter.notifyDataSetChanged()
                    }
                }

                override fun onError(error: ANError) {

                    myProgressBar!!.dismiss()

                }
         })
    }

    fun setRecycleView(){

        recycleView.setLayoutManager( LinearLayoutManager(this))
        myTicketAdapter= MyTicketAdapter(this,arrayList,MyTicketAdapter.AddTicketValue { TotalQuntity, TotalPrice,position ->
            txt_total_quantity.text= "Total Quantity: "+TotalQuntity;
            txt_total_price.text= "Total Price: $"+TotalPrice;
            this.TotalPrice=TotalPrice
            this.selectedPosition=position
            this.TotalQuntity=TotalQuntity

        })

        recycleView.adapter=myTicketAdapter

    }

     fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()

    }

}
