package com.example.a15041867.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HostHomePage extends AppCompatActivity {

    Button btnPreregister, btnCancel, btnChange;
    private String apikey, visitor_name,visitor_email,handphone_number, msg,block,unit,useremail;
    private Intent i, intentAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_home_page);

        intentAPI = getIntent();
        block = intentAPI.getStringExtra("block");
        unit = intentAPI.getStringExtra("unit");
        useremail = intentAPI.getStringExtra("user_email");


        btnPreregister = (Button)findViewById(R.id.buttonPreregister);
        btnCancel = (Button)findViewById(R.id.buttonCancel);
        btnChange = (Button)findViewById(R.id.buttonChangePassword);

        //        HttpRequest requestVisitorDetails= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php");
////                    HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php?apikey=d2bfa8e8b749d2772a21edee7b70a2b3");
//        requestVisitorDetails.setMethod("POST");
//        requestVisitorDetails.addData("apikey",apikey);
//        requestVisitorDetails.execute();

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");

        //Toast.makeText(HostHomePage.this,apikey,Toast.LENGTH_LONG).show();
//ps
        Toast.makeText(HostHomePage.this,useremail,Toast.LENGTH_SHORT).show();
        btnPreregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(HostHomePage.this, HostPreRegister.class);
                i.putExtra("apikey",apikey);
                i.putExtra("user_email",useremail);
                startActivity(i);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(HostHomePage.this, HostCancelPreRegister.class);
                i.putExtra("apikey",apikey);
                i.putExtra("user_email",useremail);
                startActivity(i);
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(HostHomePage.this, HostChangePassword.class);
                i.putExtra("apikey",apikey);
                i.putExtra("user_email",useremail);
                startActivity(i);

            }
        });



    }
}
