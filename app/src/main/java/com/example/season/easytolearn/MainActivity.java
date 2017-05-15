package com.example.season.easytolearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    static String ID = "id";
    static String EMAIL = "email";
    static String NAME = "name";
    static String NUMBER = "number";
    static String MOBILE = "phone";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();
        Long id = intent.getLongExtra(ID,0);
        User user = DataSupport.find(User.class,id);
        String name = user.getName();
        String number = user.getNumber();
        //String name = user.getName();
        Log.d("MainNumber",number);
        Log.d("MainName",name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navView.getHeaderView(0);
        TextView tv_nav_name = (TextView) headerView.findViewById(R.id.username);
        TextView tv_nav_number = (TextView) headerView.findViewById(R.id.number);
        tv_nav_name.setText(name);
        tv_nav_number.setText(number);

        CircleImageView cim_nav_icon_image = (CircleImageView) headerView.findViewById(R.id.icon_image);
        Glide.with(this).load(R.drawable.nav_icon).into(cim_nav_icon_image);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        navView.setCheckedItem(R.id.nav_work);
        replaceFragment(new WorkFragment());
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_work:
                                replaceFragment(new WorkFragment());
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_message:
                                replaceFragment(new MessageFragment());
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_center:
                                replaceFragment(new CenterFragment());
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_setting:

                                mDrawerLayout.closeDrawers();

                        }
                        return true;
                    }
                });


    }



    //主界面菜单按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.commit();
    }
}