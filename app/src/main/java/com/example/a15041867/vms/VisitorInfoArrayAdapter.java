package com.example.a15041867.vms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017082 on 19/7/2017.
 */

public class VisitorInfoArrayAdapter  extends ArrayAdapter<Visitor> {

    Context context;
    int layoutResourceId;
    ArrayList<Visitor> visitorList = null;


    public VisitorInfoArrayAdapter(Context context, int resource, ArrayList<Visitor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.visitorList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserArrayAdapter.UserHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserArrayAdapter.UserHolder();
            holder.Name = (TextView)row.findViewById(R.id.tvVisName);
            holder.HandPhone = (TextView)row.findViewById(R.id.tvVisHandPhone);
            holder.Email = (TextView)row.findViewById(R.id.tvVisEmail);

            row.setTag(holder);
        }
        else
        {
            holder = (UserArrayAdapter.UserHolder) row.getTag();
        }

        Visitor visitor = visitorList.get(position);
        holder.Name.setText(visitor.getVisitor_name());
        holder.HandPhone.setText(visitor.getVisitor_phone_number());
        holder.Email.setText(visitor.getVisitor_email());
        return row;
    }

    static class UserHolder
    {
        TextView Name;
        TextView HandPhone;
        TextView Email;
    }
}

