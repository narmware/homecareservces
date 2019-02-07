package com.narmware.doormojo;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.facebook.FacebookSdk;

/**
 * Created by rohitsavant on 05/05/18.
 */

public class MyApplication extends MultiDexApplication {

    public static final String URL_SERVER="http://doormojo.com/api/";
    //public static final String URL_SERVER="http://homecareservicesindia.com/app/api/";

    public static final String URL_GET_SERVICES=URL_SERVER+"getServices.php"; //checked
    public static final String URL_GET_SEARCH_SUBSERVICES=URL_SERVER+"getSubServices_new.php"; //changed
    public static final String URL_GET_SUBSERVICES=URL_SERVER+"getSubservicebyid_new.php"; //changed
    public static final String URL_SEND_SCHEDULE=URL_SERVER+"setappointment_new.php"; //changed
    public static final String URL_GET_BANNER_IMAGES=URL_SERVER+"getBanner.php"; //checked
    public static final String URL_LOGIN=URL_SERVER+"login.php";
    public static final String URL_REGISTER=URL_SERVER+"register.php"; //checked
    public static final String URL_MY_BOOKINGS=URL_SERVER+"mybooking_new.php"; //checked and to be changed
    public static final String URL_PAYMENT=URL_SERVER+"pay.php?"; //checked and to be changed

}
