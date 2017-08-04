package com.example.a15041867.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class DisplayVisitorInfoActivity extends AppCompatActivity {

    private static final String TAG = "DisplayVisitorInfoActivity";

    private String apikey;
    private String visitorEmail;
    private String visitorName;
    private String handphoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_visitor_info);

        Intent intent = getIntent();
        apikey = intent.getStringExtra("apikey");
        visitorEmail = intent.getStringExtra("visitor_email");
        visitorName = intent.getStringExtra("visitor_name");
        handphoneNumber = intent.getStringExtra("handphone_number");

        //TODO 04 Request User Info using HTTPRequest and Display them
        HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitorInfoByEmail.php");
        request.setMethod("POST");
        request.addData("apikey", apikey);
        request.addData("visitor_email", visitorEmail);
        request.execute();

        /******************************/

        try {
            String jsonString = request.getResponse();

            JSONObject jsonObj = new JSONObject(jsonString);

            TextView tvVisitorEmail = (TextView)findViewById(R.id.tvVisitorEmail);
            tvVisitorEmail.setText(" Visitor Email : " + jsonObj.getString("visitor_email"));
            TextView tvVisitorName = (TextView)findViewById(R.id.tvName);
            tvVisitorName.setText(" Visitor Name : " + visitorName);
            TextView tvVisitorNumber = (TextView)findViewById(R.id.tvPhone);
            tvVisitorNumber.setText(" Visitor Handphone Number : " + handphoneNumber);
            TextView tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
            tvUserEmail.setText(" Host Email : " + jsonObj.getString("user_email"));
            TextView tvSubVisitors = (TextView) findViewById(R.id.tvSubVisitor);
            String subVisitors = jsonObj.getString("sub_visitor");
            //Toast.makeText(DisplayVisitorInfoActivity.this,subVisitors, Toast.LENGTH_LONG).show();
            if(subVisitors.equals("")){
                tvSubVisitors.setText(" Sub Visitors : Nil");
            }else{
                tvSubVisitors.setText(" Sub Visitors : " + jsonObj.getString("sub_visitor"));
            }
            TextView tvDateIn = (TextView)findViewById(R.id.tvDateIn);
            tvDateIn.setText(" Date In : " + jsonObj.getString("date_in"));
            TextView tvDateOut = (TextView)findViewById(R.id.tvDateOut);
            tvDateOut.setText(" Date Out : " + jsonObj.getString("date_out"));
            TextView tvTimeIn = (TextView)findViewById(R.id.tvTimeIn);
            tvTimeIn.setText(" Time In : " + jsonObj.getString("time_in"));
            TextView tvTimeOut = (TextView)findViewById(R.id.tvTimeOut);
            tvTimeOut.setText(" Time Out : " + jsonObj.getString("time_out"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
