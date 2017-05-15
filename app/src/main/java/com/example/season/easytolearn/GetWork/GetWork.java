package com.example.season.easytolearn.GetWork;

import java.net.URI;

/**
 * Created by Season on 2017/5/10.
 */

public class GetWork {
    private String name;

    private String imageId;

    private String content;

    private String deadLine;

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public String getContent() {
        return content;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setimageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getImageId() {
        return imageId;
    }
}
