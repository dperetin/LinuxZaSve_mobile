package com.linuxzasve.mobile.googl.model;

import com.google.gson.Gson;

public class GooGlRequest {
    private String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(final String longUrl) {
        this.longUrl = longUrl;
    }

    public String toJson() {
        Gson g = new Gson();
        return g.toJson(this);
    }
}
