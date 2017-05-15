package com.example.season.easytolearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Season on 2017/5/7.
 */

public class MessageActivity extends AppCompatActivity {

    public static final String MESSAGE_NAME = "message_name";

    public static final String MESSAGE_IMAGE_ID = "message_image_id";

    public static final String MESSAGE_CONTENT = "message_content";

    String messageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        messageName = intent.getStringExtra(MESSAGE_NAME);
        String messageImageId = intent.getStringExtra(MESSAGE_IMAGE_ID);
        String messageContent = intent.getStringExtra(MESSAGE_CONTENT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView messageImageView = (ImageView) findViewById(R.id.message_image_view);
        TextView messageContentText = (TextView) findViewById(R.id.message_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar.setTitle(messageName);
        Glide.with(this).load(messageImageId).into(messageImageView);
        //Glide.with(this).load("http://192.168.123.76/" + messageImageId).into(messageImageView);
        messageContentText.setText(messageContent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}