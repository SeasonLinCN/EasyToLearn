package com.example.season.easytolearn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.season.easytolearn.UploadClient.FlaskClient;
import com.example.season.easytolearn.UploadClient.GlideImageLoader;
import com.example.season.easytolearn.UploadClient.ResponseBodySeason;
import com.example.season.easytolearn.UploadClient.ServiceGenerator;
import com.example.season.easytolearn.UploadClient.UploadResult;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Season on 2017/5/8.
 */

public class SubmitActivity extends AppCompatActivity  {

    public static final String SUBMIT_WORK_NAME = "work_name";
    public String submitWorkName = "work_name";
    private SubmitButton sBtnLoading;
    private EditText editText;

    ImageView images;
    GridView gridView;
    ImagePicker imagePicker;
    ArrayList<ImageItem> imagesList;

    String inputText = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_submit);

        Intent intent = getIntent();
        submitWorkName = intent.getStringExtra(this.submitWorkName)+".doc";
        Log.d("SubmitActivity", submitWorkName);

        editText = (EditText) findViewById(R.id.input_work);
        inputText = load();
        if (!TextUtils.isEmpty(inputText)) {
            editText.setText(inputText);
            editText.setSelection(inputText.length());
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show();
        }

        sBtnLoading = (SubmitButton) findViewById(R.id.sbtn_loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        images = bindView(R.id.images);
        gridView = bindView(R.id.gridView);

        imagesList = new ArrayList<>();
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素


        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        sBtnLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = editText.getText().toString();
                save(inputText);
                Toast.makeText(SubmitActivity.this, "正在上传", Toast.LENGTH_SHORT).show();
                uploadImages();
                uploadFiles();
            }
        });
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

    public void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(submitWorkName, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(submitWorkName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public <T extends View> T bindView(int id) {
        return (T) this.findViewById(id);
    }

    public void uploadImages() {
        if(imagesList.size() == 0) {
            Toast.makeText(SubmitActivity.this, "不能不选择图片", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //do something
                    sBtnLoading.reset();
                }
            }, 2000);    //延时2s执行
            return;
        }
        Map<String, RequestBody> files = new HashMap<>();
        final FlaskClient service = ServiceGenerator.createService(FlaskClient.class);
        for (int i = 0; i < imagesList.size(); i++) {
            File file = new File(imagesList.get(i).path);
            files.put("file" + i + "\"; filename=\"" + file.getName(), RequestBody.create(MediaType.parse(imagesList.get(i).mimeType), file));
        }
        Call<UploadResult> call = service.uploadMultipleFiles(files);
        call.enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                if (response.isSuccessful() && response.body().code == 1) {
                    sBtnLoading.doResult(true);
                    Toast.makeText(SubmitActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    Log.i("orzangleli", "---------------------上传成功-----------------------");
                    Log.i("orzangleli", "基础地址为：" + ServiceGenerator.API_BASE_URL);
                    Log.i("orzangleli", "图片相对地址为：" + listToString(response.body().image_urls,','));
                    Log.i("orzangleli", "---------------------END-----------------------");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //do something
                            sBtnLoading.reset();
                        }
                    }, 2000);    //延时2s执行
                }
            }

            @Override
            public void onFailure(Call<UploadResult> call, Throwable t) {
                sBtnLoading.doResult(false);
                Toast.makeText(SubmitActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                sBtnLoading.reset();
            }
        });

    }

    public void uploadFiles() {

        // 创建文件上传客户端
        FlaskClient service = ServiceGenerator.createService(FlaskClient.class);
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        File file = new File(getFilesDir().getPath()+"/"+submitWorkName);
        // 根据文件创建请求体
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part 还可以上传文件名
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("text", file.getName(), requestFile);
        // 在 multipart request中再添加一部分内容
        String descriptionString = "hello, this is description speaking";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // 最后执行请求
        Call<ResponseBody> call = service.uploadFiles(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 1) {
                imagesList = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                MyAdapter adapter = new MyAdapter(imagesList);
                gridView.setAdapter(adapter);
                //images.setText("已选择" + imagesList.size() + "张");
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private class MyAdapter extends BaseAdapter {

        private List<ImageItem> items;

        public MyAdapter(List<ImageItem> items) {
            this.items = items;
        }

        public void setData(List<ImageItem> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public ImageItem getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            int size = gridView.getWidth() / 3;
            if (convertView == null) {
                imageView = new ImageView(SubmitActivity.this);
                AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
                imageView.setLayoutParams(params);
                imageView.setBackgroundColor(Color.parseColor("#88888888"));
            } else {
                imageView = (ImageView) convertView;
            }
            Log.d("test","6  "+imagePicker+"  6  "+ imagePicker.getImageLoader());
            imagePicker.getImageLoader().displayImage(SubmitActivity.this, getItem(position).path, imageView, size, size);
            return imageView;
        }
    }


    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    //判断文件是否存在
    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
}
