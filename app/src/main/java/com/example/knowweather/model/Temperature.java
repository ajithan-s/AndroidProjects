package com.example.knowweather.model;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @Override
    public String toString() {
        return "Temperature{" +
                "metric=" + metric +
                '}';
    }

    @SerializedName("Metric")
    private Metric metric;

    public Temperature(Metric metric, Imperial imperial) {
        this.metric = metric;
        this.imperial = imperial;
    }

    public Imperial getImperial() {
        return imperial;
    }

    public void setImperial(Imperial imperial) {
        this.imperial = imperial;
    }

    @SerializedName("Imperial")
    private Imperial imperial;


    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
