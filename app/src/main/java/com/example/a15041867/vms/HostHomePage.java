package com.example.a15041867.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HostHomePage extends AppCompatActivity {

    Button btnPreregister, btnCancel, btnChange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_home_page);

        btnPreregister = (Button)findViewById(R.id.buttonPreregister);
        btnCancel = (Button)findViewById(R.id.buttonCancel);
        btnChange = (Button)findViewById(R.id.buttonChangePassword);

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
