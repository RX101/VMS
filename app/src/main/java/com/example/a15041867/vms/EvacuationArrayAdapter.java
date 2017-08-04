package com.example.a15041867.vms;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 15017082 on 31/7/2017.
 */

public class EvacuationArrayAdapter extends ArrayAdapter<Visitor> {

    Context context;
    int layoutResourceId;
    ArrayList<Visitor> evacuationList = null;


    public EvacuationArrayAdapter(Context context, int resource, ArrayList<Visitor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResourceId = resource;
        this.evacuationList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        evacuationHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new evacuationHolder();
            holder.Name = (TextView)row.findViewById(R.id.tvNameEva);
            holder.HandPhone = (TextView)row.findViewById(R.id.tvPhoneEva);
            holder.userEmail = (TextView)row.findViewById(R.id.tvUserEmailEva);
            holder.timeIn = (TextView)row.findViewById(R.id.tvTimeEva);
            holder.subVisitors = (TextView)row.findViewById(R.id.tvSubVisitorsEva);


            row.setTag(holder);
        }
        else
        {
            holder = (evacuationHolder) row.getTag();
        }

        Visitor visitor = evacuationList.get(position);
        //String name = visitor.getVisitor_name();
        holder.Name.setText(visitor.getVisitor_name());
        holder.HandPhone.setText(visitor.getVisitor_phone_number());
        holder.userEmail.setText(visitor.getUser_email());
        holder.timeIn.setText(visitor.getTime_in());
        holder.subVisitors.setText(visitor.getSub_visitors());
        //Toast.makeText(DisplayVisitorInfoActivity.this,subVisitors, Toast.LENGTH_LONG).show();

        return row;
    }

    static class evacuationHolder
    {
        TextView Name;
        TextView HandPhone;
        TextView userEmail;
        TextView timeIn;
        TextView subVisitors;

    }
}
