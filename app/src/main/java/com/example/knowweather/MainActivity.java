package com.example.knowweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.knowweather.model.AdministrativeArea;
import com.example.knowweather.model.Country;
import com.example.knowweather.model.Regions;
import com.example.knowweather.model.SupplementalAdminAreas;
import com.example.knowweather.model.Temperature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static android.net.NetworkInfo.*;


public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private RecyclerView recyclerView;

    MainAdapter adapter;
    Gson gson = new Gson();
    boolean connection = false;
    CustomLoader customLoader = new CustomLoader(MainActivity.this);

    private static final String domainURL = "https://dataservice.accuweather.com/";
    private static final String currentConditionMethod = "currentconditions/v1/";
    private static final String locationsMethod = "locations/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        EditText postalCodeInputEditArea = findViewById(R.id.postalCodeInputArea);
        Button getInfoButton = findViewById(R.id.getInfoButton);
        recyclerView = findViewById(R.id.postalCodeDetails);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            connection = true;
        }
        getInfoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connection) {
                    if (String.valueOf(postalCodeInputEditArea.getText()).isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please enter the postal code and then do search", Toast.LENGTH_SHORT).show();
                    } else {
                        getPostalCodeWiseData(String.valueOf(postalCodeInputEditArea.getText()));
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, Internet.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void getPostalCodeWiseData(String postalCode) {
        customLoader.startLoadingCustomDialog();
        String URL = String.format("%s%ssearch?q=%s&apikey=%s",domainURL,locationsMethod,postalCode,BuildConfig.SECRET_TOKEN);
        Log.i("URL is ", URL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.isNull(0)) {
                    customLoader.stopLoadingCustomDialog();
                    Toast.makeText(MainActivity.this, "Sorry!!!No results found", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Log.i("Array reponse", response.toString());
                    parseJsonReponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @SuppressLint("LongLogTag")
    private void parseJsonReponse(JSONArray response) {
        Log.i("Json Response is ", response.toString());
        LinkedHashMap<String, String> locationCodeWiseDetailsMap = new LinkedHashMap<>();
        List<String> supplementalDataArrangeList = new ArrayList<>();
        for (int responseObjectSize = 0; responseObjectSize < response.length(); responseObjectSize++) {
            try {
                JSONObject jsonObject = response.getJSONObject(responseObjectSize);
                String locationId = jsonObject.getString("Key");
                Regions regions = gson.fromJson(jsonObject.getJSONObject("Region").toString(), Regions.class);
                Country country = gson.fromJson(jsonObject.getJSONObject("Country").toString(), Country.class);
                AdministrativeArea administrativeArea = gson.fromJson(jsonObject.getJSONObject("AdministrativeArea").toString(), AdministrativeArea.class);
                JSONArray jsonArray = jsonObject.getJSONArray("SupplementalAdminAreas");
                if (jsonArray.length() == 0) {
                    locationCodeWiseDetailsMap.put(locationId, String.format("%s,%s,%s", administrativeArea.getEnglishName(), country.getEnglishName(), regions.getEnglishName()));
                } else {
                    Type listType = new TypeToken<ArrayList<SupplementalAdminAreas>>() {
                    }.getType();
                    ArrayList<SupplementalAdminAreas> supplementAdminAreasArrayList = gson.fromJson(jsonArray.toString(), listType);
                    Log.i("SupplemetalalArrayListSize", String.valueOf(supplementAdminAreasArrayList.size()));
                    for (SupplementalAdminAreas supplementalAdminAreas : supplementAdminAreasArrayList) {
                        supplementalDataArrangeList.add(supplementalAdminAreas.getEnglishName());
                        Log.i("SupplementalAdminAreas", supplementalAdminAreas.getEnglishName());
                    }
                    String addString = "";
                    for (int supplementalLevelDataIndex = supplementalDataArrangeList.size() - 1; supplementalLevelDataIndex >= 0; supplementalLevelDataIndex--) {
                        addString = addString + supplementalDataArrangeList.get(supplementalLevelDataIndex) + ",";
                    }
                    supplementalDataArrangeList.clear();
                    locationCodeWiseDetailsMap.put(locationId,String.format("%s%s,%s,%s", addString, administrativeArea.getEnglishName(), country.getEnglishName(), regions.getEnglishName()));
                }
                Log.i("RegionId", regions.getId());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        List<String> keyLocationWiseMap = new ArrayList<>(locationCodeWiseDetailsMap.keySet());
        List<String> valueLocationWiseMap = new ArrayList<>(locationCodeWiseDetailsMap.values());
        Log.i("Value List is ", String.valueOf(locationCodeWiseDetailsMap.values()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this, keyLocationWiseMap,valueLocationWiseMap);
        recyclerView.setAdapter(adapter);
        customLoader.stopLoadingCustomDialog();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                passWeatherDataToDisplayResult(keyLocationWiseMap.get(viewHolder.getAdapterPosition()));
                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
        Log.i("Recycler String", String.valueOf(locationCodeWiseDetailsMap));
    }

    private void passWeatherDataToDisplayResult(String locationKey){
        customLoader.startLoadingCustomDialog();
        String URL = String.format("%s%s%s?apikey=%s",domainURL,currentConditionMethod,locationKey, BuildConfig.SECRET_TOKEN);
        Log.i("URL is ",URL);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("Weather_Result", response.toString());
                callAnotherActivity(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void callAnotherActivity(JSONArray jsonArray){
        try {
            JSONObject weatherDataJsonObject = jsonArray.getJSONObject(0);
            Temperature temperature = gson.fromJson(weatherDataJsonObject.getJSONObject("Temperature").toString(), Temperature.class);
            Log.i("Temperature_details",temperature.toString());
            Intent intent = new Intent(MainActivity.this,DisplayResult.class);
            intent.putExtra("WeatherCeliusData",String.format("%s%s%s",temperature.getMetric().getValue(),"\u00B0",temperature.getMetric().getUnit()));
            intent.putExtra("WeatherFarenheitData",String.format("%s%s%s",temperature.getImperial().getValue(),"\u00B0",temperature.getImperial().getUnit()));
            intent.putExtra("WeatherStatus",weatherDataJsonObject.getString("WeatherText"));
            customLoader.stopLoadingCustomDialog();
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}