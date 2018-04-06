package com.opera.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 59851 on 7/29/2016.
 */
public class Connections {


    public static boolean isConnectionAlive(Context context)
    {
        try
        {
            ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null)
            {

                NetworkInfo ni = connectivity.getActiveNetworkInfo();
                if (ni == null){
                    // There are no active networks.
                    return false;
                } else
                    return true;
            }
        }
        catch(Exception e)
        {
            OperaUtils.printException(e);
        }
        return false;
    }

}
