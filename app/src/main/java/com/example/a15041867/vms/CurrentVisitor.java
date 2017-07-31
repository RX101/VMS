package com.example.a15041867.vms;

/**
 * Created by 15041867 on 24/7/2017.
 */

public class CurrentVisitor {
    private String visitor_email;
    private String sub_visitor;
    private String date_in;
    private String time_in;

    public CurrentVisitor(){

    }

    public CurrentVisitor(String visitor_email, String sub_visitor, String date_in, String time_in) {
        this.visitor_email = visitor_email;
        this.sub_visitor = sub_visitor;
        this.date_in = date_in;
        this.time_in = time_in;
    }

    public String getVisitor_email() {
        return visitor_email;
    }

    public void setVisitor_email(String visitor_email) {
        this.visitor_email = visitor_email;
    }

    public String getSub_visitor() {
        return sub_visitor;
    }

    public void setSub_visitor(String sub_visitor) {
        this.sub_visitor = sub_visitor;
    }

    public String getDate_in() {
        return date_in;
    }

    public void setDate_in(String date_in) {
        this.date_in = date_in;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    @Override
    public String toString() {
        return "Visitor Email: " + visitor_email + "\n"
                + "Sub visitor: " + sub_visitor + "\n"
                + "Time: " + date_in + " " + time_in;
    }
}
