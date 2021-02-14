package com.example.knowweather;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class CustomLoader {
    private Activity activity;
    AlertDialog alertDialog;

    CustomLoader(Activity myActivity){
        this.activity = myActivity;
    }

    void startLoadingCustomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.custom_loader,null));
        builder.setCancelable(true);

        alertDialog = builder.create();
        alertDialog.show();
    }

    void stopLoadingCustomDialog(){
        alertDialog.dismiss();
    }
}
