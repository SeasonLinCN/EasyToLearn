package com.example.season.easytolearn.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.season.easytolearn.R;
import com.example.season.easytolearn.User;

import org.litepal.crud.DataSupport;

/**
 * Created by Season on 2017/5/15.
 */

public class ChangePasswordActivity extends AppCompatActivity{
    EditText newPsw;
    EditText confirmPsw;
    Long id;
    static String ID = "id";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Intent intent = getIntent();
        id = intent.getLongExtra(ID,0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        newPsw = (EditText)findViewById(R.id.change);
        confirmPsw = (EditText)findViewById(R.id.confirm);
        Button btn_change = (Button)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate()){
                    Toast.makeText(getApplicationContext(),"Fail To Change Psw!",Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = DataSupport.find(User.class,id);
                user.setPassword(newPsw.getText().toString());
                user.update(id);
                finish();
                Toast.makeText(getApplicationContext(),"Succeed To Change Psw!",Toast.LENGTH_SHORT).show();

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

    public boolean validate() {
        boolean valid = true;
        String password = newPsw.getText().toString();
        String reEnterPassword = confirmPsw.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            newPsw.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            newPsw.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            confirmPsw.setError("Password Do not match");
            valid = false;
        } else {
            confirmPsw.setError(null);
        }

        return valid;
    }
}
