package com.example.android.bakingapp;

import java.io.Serializable;

public class Step implements Serializable {
    public int id;
    public String shortDescription;
    public String description;
    public String videoUrl;
    public String thumbnailUrl;

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}
