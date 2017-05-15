package com.example.season.easytolearn;

/**
 * Created by Season on 2017/5/5.
 */



import java.util.HashMap;

        import android.app.Activity;
import android.os.Bundle;
        import android.util.Log;
        import android.view.View;

import cn.smssdk.EventHandler;
        import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class RegisterActivity extends Activity {
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SMSSDK.initSDK(this, "1d962447b8d86",
                "febc328b8a0f1763b4ec860f40b773a8");

    }

    public void register(View view) {
        // 打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String phone = (String) phoneMap.get("phone");

                    phoneNumber = phone;

                    Log.e("PhoneNumber", phone);

                }
            }
        });
        registerPage.show(RegisterActivity.this);
    }
}