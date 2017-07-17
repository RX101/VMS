package com.example.a15041867.vms;

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

    public User(String user_email, String name, String handphone_number, String position, String block, String unit, String password  ){
        this.user_email = user_email;
        this.name = name;
        this.handphone_number = handphone_number;
        this.position = position;
        this.block = block;
        this.unit = unit;
        this.password = password;
    }

    public String getUser_email(){
        return user_email;
    }

    public String getName(){
        return name;
    }

    public String getHandphone_number(){
        return handphone_number;
    }

    public String getPosition(){
        return position;
    }

    public String getBlock(){
        return block;
    }

    public String getUnit(){
        return unit;
    }

    public String getPassword(){
        return password;
    }
}
