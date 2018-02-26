package com.example.software.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.software.activity.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by jafeth on 3/31/17.
 */

public class Utils {

    // Find Image ID corresponding to the name of the image (in the directory mipmap).
    public static int getResourceIdByName(Context context, String resName, String defType)  {
        //defType: drawable, mipmap
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , defType, pkgName);
        Log.i("getResourceIdByName", "Res Name: "+ resName+" ==> Res ID= "+ resID);
        return resID;
    }

    public static void getPackageName(Context context) {
        String packageName = context.getPackageName();
        Toast.makeText(context, packageName, Toast.LENGTH_LONG).show();
    }

    public static void shortToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static Gson gsonBuilder() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();
        return gson;
    }


}
