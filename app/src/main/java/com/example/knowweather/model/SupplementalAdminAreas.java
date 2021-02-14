package com.example.knowweather.model;

import com.google.gson.annotations.SerializedName;

public class SupplementalAdminAreas {
    @SerializedName("Level")
    private int level;
    @SerializedName("EnglishName")
    private String englishName;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public SupplementalAdminAreas(int level, String englishName) {
        this.level = level;
        this.englishName = englishName;
    }
}
