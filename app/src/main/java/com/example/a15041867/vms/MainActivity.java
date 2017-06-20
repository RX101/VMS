package com.example.a15041867.vms;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etLoginEmail, etLoginPassword;
    private TextView tvForgetPassword;
    private Session session;
    Intent i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin =(Button)findViewById(R.id.buttonLogin);
        etLoginEmail = (EditText)findViewById(R.id.editTextloginEmail);
        etLoginPassword = (EditText)findViewById(R.id.editTextloginPassword);
        tvForgetPassword = (TextView) findViewById(R.id.textViewForgetPassword);
        session = new Session(this);
        //               if(session.loggedin()) {
//                    startActivity(i);
//                    finish();
//
//                }

    }


}
