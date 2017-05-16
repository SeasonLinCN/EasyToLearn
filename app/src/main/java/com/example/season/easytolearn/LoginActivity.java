package com.example.season.easytolearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;
import java.util.logging.Handler;

import butterknife.ButterKnife;
import butterknife.Bind;
/**
 * Created by Season on 2017/5/4.
 */

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    long id;
    String email;
    String name;
    String mobile;
    String number;
    String password;

    private SharedPreferences pref;
    private CheckBox rememberPass;
    private SharedPreferences.Editor editor;

    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        String oldPassword;
        Intent intent = getIntent();
        oldPassword = intent.getStringExtra("password");
        String phone = intent.getStringExtra("phone");
        if(oldPassword != null){
            _passwordText.setText(oldPassword);
            _mobileText.setText(phone);
        }

        Connector.getDatabase();

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);

        if(TextUtils.isEmpty(_mobileText.getText())){
            boolean isRemember = pref.getBoolean("remember_password", false);
            if (isRemember) {
                // 将账号和密码都设置到文本框中
                mobile = pref.getString("mobile", "");
                String password = pref.getString("password", "");
                _mobileText.setText(mobile);
                _passwordText.setText(password);
                rememberPass.setChecked(true);
            }
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();

        editor = pref.edit();
        if (rememberPass.isChecked()) { // 检查复选框是否被选中
            editor.putBoolean("remember_password", true);
            editor.putString("mobile", mobile);
            editor.putString("password", password);
        } else {
            editor.clear();
        }
        editor.apply();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        mobile = _mobileText.getText().toString();
        List<User> users = DataSupport.findAll(User.class);
        for (User user:users){
            if(user.getMobile().equals(mobile)){
                id = user.getId();
                name = user.getName();
                number = user.getNumber();
                email = user.getEmail();
            }
        }
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra(MainActivity.ID, id);
        intent.putExtra(MainActivity.EMAIL, email);
        intent.putExtra(MainActivity.NAME, name);
        intent.putExtra(MainActivity.NUMBER, number);
        intent.putExtra(MainActivity.MOBILE, mobile);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = false;
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();

        List<User> users = DataSupport.findAll(User.class);
        for(User user:users){
            if (!mobile.isEmpty() &&mobile.equals(user.getMobile())) {
                _mobileText.setError(null);
                if (!password.isEmpty() && password.length() >= 4 && password.length() <= 10 &&password.equals(user.getPassword())) {
                    _passwordText.setError(null);
                    valid= true;
                } else {
                    _passwordText.setError("between 4 and 10 alphanumeric characters");
                }
            } else {
                _mobileText.setError("enter a valid mobile ");
            }
        }
        return valid;
    }
}

