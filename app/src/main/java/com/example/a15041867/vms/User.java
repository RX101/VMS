package com.example.a15041867.vms;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 15017082 on 10/7/2017.
 */

public class User {

    private String user_email;
    private String name;
    private String handphone_number;
    private String position;
    private String block;
    private String unit;
    private String password;

    public User(){
        super();
    }

    public String getUser_email(){
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getHandphone_number(){
        return handphone_number;
    }
    public void setHandphone_number(String handphone_number) {
        this.handphone_number = handphone_number;
    }

    public String getPosition(){
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getBlock(){
        return block;
    }
    public void setBlock(String block) {
        this.block = block;
    }

    public String getUnit(){
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
