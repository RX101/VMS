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
 * Created by 15017082 on 7/8/2017.
 */

public class VisitorInfoDateArrayAdapter extends ArrayAdapter<Visitor> {

    Context context;
    int layoutResourceId;
    ArrayList<Visitor> visitorList = null;


    public VisitorInfoDateArrayAdapter(Context context, int resource, ArrayList<Visitor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.visitorList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        VisitorInfoDateArrayAdapter.visitorHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new VisitorInfoDateArrayAdapter.visitorHolder();
            holder.Email = (TextView)row.findViewById(R.id.tvVisEmailDat);
            holder.subVisitor = (TextView)row.findViewById(R.id.tvSubVisDat);
            holder.userEmail = (TextView)row.findViewById(R.id.tvUserDat);

            row.setTag(holder);
        }
        else
        {
            holder = (VisitorInfoDateArrayAdapter.visitorHolder) row.getTag();
        }

        Visitor visitor = visitorList.get(position);
        holder.Email.setText(visitor.getVisitor_email());
        holder.subVisitor.setText(visitor.getSub_visitors());
        holder.userEmail.setText(visitor.getUser_email());
        return row;
    }

    static class visitorHolder
    {
        TextView Email;
        TextView subVisitor;
        TextView userEmail;
    }
}
