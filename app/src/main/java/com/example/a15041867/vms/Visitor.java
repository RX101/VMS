package com.example.a15041867.vms;

/**
 * Created by 15039840 on 12/7/2017.
 */

public class Visitor {
    private String visitor_name;
    private String visitor_phone_number;
    private String visitor_email;

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


    public String getVisitor_name(){
        return visitor_name;
    }

    public String getVisitor_phone_number(){
        return visitor_phone_number;
    }

    public String getVisitor_email(){
        return visitor_email;
    }


    //To display in list view
   // public String toString(){
    //    return
     //           visitor_name + '\n' + visitor_email;

//    }

}
