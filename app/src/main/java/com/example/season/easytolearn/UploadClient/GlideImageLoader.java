package com.example.season.easytolearn.UploadClient;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.season.easytolearn.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Season on 2017/5/9.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)//
                .load(path.contains("http")?path:new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .fitCenter()
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
    }
}
