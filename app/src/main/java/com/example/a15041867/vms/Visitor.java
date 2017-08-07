package com.example.a15041867.vms;

/**
 * Created by 15039840 on 12/7/2017.
 */

public class Visitor {
    private int id;
    private String visitor_name;
    private String visitor_phone_number;
    private String visitor_email;
    private String time_in;
    private String date_in;
    private String user_email;
    private String sub_visitors;

    public Visitor(){
        super();
    }


    public void setVisitor_name(String visitor_name) {
        this.visitor_name = visitor_name;
    }
    public void setVisitor_phone_number(String visitor_phone_number) {
        this.visitor_phone_number = visitor_phone_number;
    }
    public void setVisitor_email(String visitor_email) {
        this.visitor_email = visitor_email;
    }

    public void setTime_in(String time_in){this.time_in=time_in;}
    public void setDate_in(String date_in){this.date_in=date_in;}
    public void setUser_email(String user_email){this.user_email=user_email;}
    public void setSub_visitors(String sub_visitors){this.sub_visitors=sub_visitors;}
//a

    public String getVisitor_name(){
        return visitor_name;
    }

    public int getId(){
        return id;
    }
    public String getVisitor_phone_number(){
        return visitor_phone_number;
    }

    public String getVisitor_email(){
        return visitor_email;
    }


    public String getTime_in(){ return time_in; }
    public String getDate_in() {return date_in; }
    public String getUser_email() {return user_email; }
    public String getSub_visitors() {return sub_visitors; }



    //To display in list view
   // public String toString(){
    //    return
     //           visitor_name + '\n' + visitor_email;

//    }

}
