package com.example.season.easytolearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Season on 2017/5/7.
 */

public class WorkActivity extends AppCompatActivity {

    public static final String WORK_NAME = "work_name";

    public static final String WORK_IMAGE_ID = "work_image_id";

    public static final String WORK_CONTENT = "work_content";

    String workName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        /*从Intent中获取作业名称、内容与图片*/
        Intent intent = getIntent();
        workName = intent.getStringExtra(WORK_NAME);
        String workImageId = intent.getStringExtra(WORK_IMAGE_ID);
        String workContent = intent.getStringExtra(WORK_CONTENT);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView workImageView = (ImageView) findViewById(R.id.work_image_view);
        TextView workContentText = (TextView) findViewById(R.id.work_content_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*加载作业名称、内容与图片*/
        collapsingToolbar.setTitle(workName);
        Glide.with(this).load("http://192.168.123.76/" + workImageId).into(workImageView);
        workContentText.setText(workContent);

        /*提交作业悬浮按钮*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkActivity.this,SubmitActivity.class);
                intent.putExtra(SubmitActivity.SUBMIT_WORK_NAME, workName);
                startActivity(intent);
            }
        });
    }

    /*回退到*/
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