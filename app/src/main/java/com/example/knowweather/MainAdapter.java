package com.example.knowweather;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    Activity activity;
    List<String> keyLocationWise;
    List<String> valueLocationWise;
    public MainAdapter(Activity activity,List<String> keyLocationWise,List<String> valueLocationWise){
        this.activity = activity;
        this.keyLocationWise = keyLocationWise;
        this.valueLocationWise = valueLocationWise;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("a","onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("b","onBindViewHolder");
        holder.textView.setText(valueLocationWise.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(activity,"Swipe to right side to get the weather details", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.i("KeyListSize", String.valueOf(keyLocationWise.size()));
        return keyLocationWise.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardViewTextView);
        }
    }
}
