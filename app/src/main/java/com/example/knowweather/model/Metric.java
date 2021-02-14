package com.example.knowweather.model;

import com.google.gson.annotations.SerializedName;

public class Metric {
    @SerializedName("Value")
    private String value;
    @SerializedName("Unit")
    private String unit;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Metric(String value, String unit) {
        this.value = value;
        this.unit = unit;
    }


}
