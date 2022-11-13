package com.app.sportsappjava.Utlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

public class CommonFunctions {


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static void showNoInternetDialog(Activity context) {
        showNoInternetDialog(context, false);
    }

    public static void showNoInternetDialog(final Activity context, final boolean close) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please check your internet connection!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (close) {
                            context.finish();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();


    }

}
