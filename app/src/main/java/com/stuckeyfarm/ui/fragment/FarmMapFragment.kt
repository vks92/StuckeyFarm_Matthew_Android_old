package com.stuckeyfarm.ui.fragment


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment

import com.stuckeyfarm.R
import com.stuckeyfarm.utills.MySharedPreference
import kotlinx.android.synthetic.main.fragment_farm_map.*
import kotlinx.android.synthetic.main.fragment_farm_map.view.*

class FarmMapFragment : Fragment(), View.OnClickListener {

    var rootView: View? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout. fragment_farm_map, container, false)

        return rootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setlistener()
        initWebView()

        ll_root.setBackgroundColor(Color.parseColor(MySharedPreference(activity).bgColor))
    }

    private fun setlistener() {

        rootView!!.ll_back.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            rootView!!.ll_back.id ->{ activity?.onBackPressed()}
        }
    }

     @SuppressLint("SetJavaScriptEnabled")
     fun initWebView(){
         var yourData="<!DOCTYPE html>\n" +
                 "<html>\n" +
                 "<head>\n" +
                 "\n" +
                 "</head>\n" +
                 "\n" +
                 "\n" +
                 "<body style=\"margin:0px;padding:0px;overflow:hidden\">\n" +
                 "    <iframe \n" +
                 "    src=\"https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d3489.5481520629223!2d-86.24116285004071!3d40.076153521432886!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x8dfc7d84e4b3feb3!2sStuckey+Farm+Market!5e0!3m2!1sen!2sin!4v1541140123500\" \n" +
                 "    frameborder=\"0\" style = \"display: block;border: none;height: 100vh; width: 100vw;\"; height=\"100%\" width=\"100%\"></iframe>\n" +
                 "</body>\n" +
                 "\n" +
                 "</html>"



         val newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0"


         webView.settings.userAgentString = newUA

         webView.settings.javaScriptEnabled = true
         webView.settings.builtInZoomControls = false
         webView.settings.displayZoomControls = false
         webView.settings.setAppCacheEnabled(true)

         // webView.setListener(this, this);
         webView.settings.pluginState = WebSettings.PluginState.ON
         webView.webChromeClient = object : WebChromeClient() {
             override fun onProgressChanged(view: WebView, newProgress: Int) {
                 super.onProgressChanged(view, newProgress)
                 // Your custom code.
                 if (newProgress < 100) {

                 } else {

                 }

             }
         }
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             webView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
         }
         webView.loadData(yourData, "text/html", "UTF-8");

     }


}
