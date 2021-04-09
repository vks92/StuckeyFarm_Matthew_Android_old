package com.stuckeyfarm.api

 object API {

    ///// Live Url

   // private val baseUrl = "http://reseller.memomarketinggroup.com/stuckeyFarm/public/api/"

   private val baseUrl = "https://farmmarketapp.com/stuckey/public/api/"

   // private val baseImageUrl = "http://reseller.memomarketinggroup.com/stuckeyFarm/public/images/"

    private val baseImageUrl = "https://farmmarketapp.com/stuckey/public/images/"

    //////// Local URL

    // private static String   baseUrl="http://192.168.1.76/stuckeyFarm/public/api/";
   // private val BaseImageURL = "http://192.168.1.76/stuckeyFarm/public/images/"

    val DashBoardImgUrl = baseImageUrl + "appDashboard/"
    val AboutUsImageUrl = baseImageUrl + "aboutUs/"
    val PiskListImageUrl = baseImageUrl + "pickList/"
    val CatImageUrl = baseImageUrl + "category/"
    val MapImageBaseUrl = baseImageUrl + "pickMap/"

    val GET_CATEGORY = baseUrl + "get_category"
    val GET_PICK_LIST = baseUrl + "get_pick_list"
    val GET_EVENT = baseUrl + "get_event"
    val GET_TICKET = baseUrl + "get_ticket"

    val GET_DASHBOARD = baseUrl + "appDashboard"
    val GET_ABOUT_US = baseUrl + "aboutUs"
    val TOKEN_UPDATE = baseUrl + "update-token"

    val GET_THEME_COLOR = baseUrl + "theme_Color"

    val geThemeDataURL = baseUrl + "theme_Color"
    val getDashBoardURL = baseUrl + "appDashboard"
    val getCategoryURL = baseUrl + "get_category"
    val getPickListURL = baseUrl + "get_pick_list"
    val getEventURL    = baseUrl + "get_event"
    val getTicketURL   = baseUrl + "get_ticket?event_id="
    val getPurchasedTicketURL = baseUrl + "get-tickets-by-id?mobile_id="
    val getAboutUs   = baseUrl + "aboutUs"
    val postTokenURL = baseUrl + "update-token"
    val paymentProcessURL = baseUrl + "process-payment"
    val squarePaymentProcessURL = baseUrl + "process-payment-square"

 }