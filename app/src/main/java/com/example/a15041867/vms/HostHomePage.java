package com.example.a15041867.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HostHomePage extends AppCompatActivity {

    Button btnPreregister, btnCancel, btnChange;
    private String apikey, visitor_name,visitor_email,handphone_number, msg;
    private Intent i, intentAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_home_page);

        btnPreregister = (Button)findViewById(R.id.buttonPreregister);
        btnCancel = (Button)findViewById(R.id.buttonCancel);
        btnChange = (Button)findViewById(R.id.buttonChangePassword);

        HttpRequest requestVisitorDetails= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php");
//                    HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php?apikey=d2bfa8e8b749d2772a21edee7b70a2b3");
        requestVisitorDetails.setMethod("POST");
        requestVisitorDetails.addData("apikey",apikey);
        requestVisitorDetails.execute();
//ps
        btnPreregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HostHomePage.this, HostPreRegister.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HostHomePage.this, HostCancelPreRegister.class));
            }
        });



    }
}
