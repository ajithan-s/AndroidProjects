package com.example.knowweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Internet extends AppCompatActivity {
    Button refreshConnectionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        refreshConnectionButton = findViewById(R.id.refreshConnectionButton);
        refreshConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()){
                    Intent intent = new Intent(Internet.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Internet.this,"Check your device is receiving internet!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}