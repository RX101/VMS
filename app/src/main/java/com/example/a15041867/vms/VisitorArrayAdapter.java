package com.example.a15041867.vms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 15039840 on 12/7/2017.
 */

public class VisitorArrayAdapter extends ArrayAdapter<Visitor> {

    Context context;
    ArrayList<Visitor> visitors;
    TextView tvNRIC, tvName, tvPhoneNumber, tvEmail, tvDate, tvTime;
    int resource;

    public VisitorArrayAdapter(Context context, int resource, ArrayList<Visitor> visitors) {
        super(context, resource, visitors);
        this.context = context;
        this.visitors = visitors;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//d
        View rowView = inflater.inflate(resource, parent, false);

        //Match the UI components with Java variables
        tvName = (TextView) rowView.findViewById(R.id.tvName);
        tvPhoneNumber = (TextView) rowView.findViewById(R.id.tvPhoneNumber);
        tvEmail = (TextView) rowView.findViewById(R.id.tvEmail);
        tvTime = (TextView) rowView.findViewById(R.id.tvTimeIn);
        tvDate = (TextView) rowView.findViewById(R.id.tvDateIn);

        Visitor visitor1 = visitors.get(position);
        String name = visitor1.getVisitor_name();
        tvName.setText(name);
        String phoneNumber = visitor1.getVisitor_phone_number();
        tvPhoneNumber.setText(phoneNumber);
        String email = visitor1.getVisitor_email();
        tvEmail.setText(email);
        String datein = visitor1.getDate_in();
        tvDate.setText(datein);
        String timein = visitor1.getTime_in();
        tvTime.setText(timein);

        return rowView;
    }

}