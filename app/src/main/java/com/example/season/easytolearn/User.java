package com.example.season.easytolearn;

import android.net.Uri;

import com.lzy.imagepicker.bean.ImageItem;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Season on 2017/5/8.
 */

public class User extends DataSupport{
    private long id;//litepal中id只能为int或long
    private String name;
    private String number;
    private String email;
    private String mobile;
    private String password;
    private String icon;
    private String university;

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setMajorClass(String majorClass) {
        this.majorClass = majorClass;
    }

    public String getUniversity() {

        return university;
    }

    public String getSchool() {
        return school;
    }

    public String getMajorClass() {
        return majorClass;
    }

    private String school;
    private String majorClass;

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {

        return icon;
    }

    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile() {

        return mobile;
    }


    public String getPassword() {

        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}


