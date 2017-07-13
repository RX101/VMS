package com.example.a15041867.vms;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HostPreRegister extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    Intent i;
    EditText etNumber, etName, etEmail, etTime, etDate, etHostEmail;

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_pre_register);

        nv = (NavigationView) findViewById(R.id.nvPreRegister);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutPreRegister);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.nav_pre_register):
                        i = new Intent(getApplicationContext(), HostPreRegister.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_cancel_pre_register):
                        i = new Intent(getApplicationContext(), HostCancelPreRegister.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_change_password):
                        i = new Intent(getApplicationContext(), HostChangePassword.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNumber = (EditText)findViewById(R.id.editTextPhoneNumber);
                etName = (EditText)findViewById(R.id.editTextName);
                etEmail = (EditText)findViewById(R.id.editTextEmail);
                etTime = (EditText)findViewById(R.id.editTextTime);
                etDate = (EditText)findViewById(R.id.editTextDate);
                etHostEmail = (EditText)findViewById(R.id.editTextHostEmail);
                HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/insertVisitor.php");
                request.setMethod("POST");
                request.addData("visitor_email",etEmail.getText().toString());
                request.addData("visitor_name",etName.getText().toString());
                request.addData("handphone_number",etNumber.getText().toString());
                request.execute();
                try{
                    HttpRequest request1 = new HttpRequest("http://ruixian-ang97.000webhostapp.com/insertVisitorInfo.php");
                    request1.setMethod("POST");
                    request1.addData("visitor_email",etEmail.getText().toString());
                    request1.addData("date_in",etDate.getText().toString());
                    request1.addData("time_in",etTime.getText().toString());
                    request1.addData("user_email",etHostEmail.getText().toString());
                    request1.execute();
                    Toast.makeText(HostPreRegister.this,"Visitor inserted",Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    } //yo

    protected void onStart(){
        super.onStart();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
