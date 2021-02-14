package com.example.knowweather.model;

import com.google.gson.annotations.SerializedName;

public class AdministrativeArea {
    @SerializedName("ID")
    private  String id;
    @SerializedName("LocalizedName")
    private String localizedName;
    @SerializedName("EnglishName")
    private String englishName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public AdministrativeArea(String id, String localizedName, String englishName) {
        this.id = id;
        this.localizedName = localizedName;
        this.englishName = englishName;
    }
}
