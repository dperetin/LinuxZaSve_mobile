package com.linuxzasve.mobile.googl.model;

import com.google.gson.Gson;

public class GooGlResponse {
    private String kind;
    private String id;
    private String longUrl;

    public String getKind() {
        return kind;
    }

    public void setKind(final String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(final String longUrl) {
        this.longUrl = longUrl;
    }

    public static GooGlResponse fromJson(final String json) {
        Gson g = new Gson();
        return g.fromJson(json, GooGlResponse.class);
    }
}
