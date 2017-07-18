package com.example.a15041867.vms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15017082 on 17/7/2017.
 */

public class UserArrayAdapter extends ArrayAdapter<User> {

    Context context;
    int layoutResourceId;
    ArrayList<User> userList = null;


    public UserArrayAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.userList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new UserHolder();
            holder.Name = (TextView)row.findViewById(R.id.tvName);
            holder.HandPhone = (TextView)row.findViewById(R.id.tvHandPhone);
            holder.Email = (TextView)row.findViewById(R.id.tvEmail);

            row.setTag(holder);
        }
        else
        {
            holder = (UserHolder) row.getTag();
        }

        User user = userList.get(position);
        holder.Name.setText(user.getUser_email());
        holder.HandPhone.setText(user.getHandphone_number());
        holder.Email.setText(user.getHandphone_number());
        return row;
    }

    static class UserHolder
    {
        TextView Name;
        TextView HandPhone;
        TextView Email;
    }
}
