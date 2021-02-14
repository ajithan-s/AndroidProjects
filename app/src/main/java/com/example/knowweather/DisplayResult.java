package com.example.knowweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayResult extends AppCompatActivity {

    TextView weatherStatus,weatherCeliusValue,weatherFarenheitValue;
    Button viewAnotherResultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);

        weatherStatus = findViewById(R.id.weatherStatusTextView);
        weatherCeliusValue = findViewById(R.id.celiusDegreeId);
        weatherFarenheitValue = findViewById(R.id.farenheitDegreeId);
        viewAnotherResultButton = findViewById(R.id.viewAnotherResult);

        weatherCeliusValue.setText(getIntent().getStringExtra("WeatherCeliusData"));
        weatherFarenheitValue.setText(getIntent().getStringExtra("WeatherFarenheitData"));
        weatherStatus.setText(getIntent().getStringExtra("WeatherStatus"));

        viewAnotherResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayResult.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}