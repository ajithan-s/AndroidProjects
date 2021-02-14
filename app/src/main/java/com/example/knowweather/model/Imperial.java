package com.example.knowweather.model;

import com.google.gson.annotations.SerializedName;

public class Imperial {
    public Imperial(String value, String unit) {
        this.value = value;
        this.unit = unit;
    }

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

    @SerializedName("Value")
    private String value;
    @SerializedName("Unit")
    private String unit;
}
