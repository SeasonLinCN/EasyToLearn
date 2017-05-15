package com.example.season.easytolearn;

/**
 * Created by Season on 2017/5/4.
 */

public class Message {
    private String name;

    private String imageId;

    private String content;

    private String time;

    public void setName(String name) {
        this.name = name;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public Message(String name, String imageId, String content, String time) {
        this.name = name;
        this.imageId = imageId;
        this.content = content;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }
}
