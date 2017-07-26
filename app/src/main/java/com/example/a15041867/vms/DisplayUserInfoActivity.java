package com.example.a15041867.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONObject;

public class DisplayUserInfoActivity extends AppCompatActivity {
    private static final String TAG = "DisplayUserInfoActivity";


    private String apikey;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user_info);

        Intent intent = getIntent();
        apikey = intent.getStringExtra("apikey");
        userEmail = intent.getStringExtra("user_email");

        //TODO 04 Request User Info using HTTPRequest and Display them
        HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getUserByEmail.php");
        request.setMethod("POST");
        request.addData("apikey", apikey);
        request.addData("user_Email", userEmail);
        request.execute();

        /******************************/

        try {
            String jsonString = request.getResponse();
            Log.d(TAG, "jsonString: " + jsonString);

            JSONObject jsonObj = new JSONObject(jsonString);

            EditText userName = (EditText)findViewById(R.id.etUserName);
            userName.setText(jsonObj.getString("name"));

            EditText userEmail = (EditText)findViewById(R.id.etEmail);
            userEmail.setText(jsonObj.getString("user_email"));

            EditText userPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);
            userPhoneNumber.setText(jsonObj.getString("handphone_number"));

            EditText userBlock = (EditText)findViewById(R.id.etBlock);
            userBlock.setText(jsonObj.getString("block"));

            EditText userUnit = (EditText)findViewById(R.id.etUnit);
            userUnit.setText(jsonObj.getString("unit"));

            String role = jsonObj.getString("position");

            RadioButton rbHost = (RadioButton)findViewById(R.id.rbHost);
            String rbHostTitle = rbHost.getText().toString();
            if(role.equals(rbHostTitle)){
                rbHost.setChecked(true);
            } else {
                rbHost.setChecked(false);
            }
            RadioButton rbSecurity = (RadioButton)findViewById(R.id.rbSecurity);
            String rbSecurityTitle = rbSecurity.getText().toString();
            if(role.equals(rbSecurityTitle)){
                rbSecurity.setChecked(true);
            } else {
                rbSecurity.setChecked(false);
            }
            RadioButton rbManager = (RadioButton)findViewById(R.id.rbManager);
            String rbManagerTitle = rbManager.getText().toString();
            if(role.equals(rbManagerTitle)){
                rbManager.setChecked(true);
            } else {
                rbManager.setChecked(false);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
