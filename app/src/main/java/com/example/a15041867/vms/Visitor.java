package com.example.a15041867.vms;

/**
 * Created by 15039840 on 12/7/2017.
 */

public class Visitor {
    private String visitor_name;
    private String visitor_phone_number;
    private String visitor_email;
    private String time_in;
    private String date_in;

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
//a

    public String getVisitor_name(){
        return visitor_name;
    }

    public String getVisitor_phone_number(){
        return visitor_phone_number;
    }

    public String getVisitor_email(){
        return visitor_email;
    }

    public String getTime_in(){ return time_in; }
    public String getDate_in() {return date_in; }



    //To display in list view
   // public String toString(){
    //    return
     //           visitor_name + '\n' + visitor_email;

//    }

}
