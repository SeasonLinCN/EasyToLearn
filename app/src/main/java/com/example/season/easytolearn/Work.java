package com.example.season.easytolearn;

import android.net.Uri;

import java.io.File;
import java.net.URI;

/**
 * Created by Season on 2017/5/4.
 */

public class Work {
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


    Work(String name, String imageId, String content, String deadLine) {
        this.name = name;
        this.imageId = imageId;
        this.content = content;
        this.deadLine = deadLine;
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
