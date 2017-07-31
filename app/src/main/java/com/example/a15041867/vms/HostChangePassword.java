package com.example.a15041867.vms;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class HostChangePassword extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    String apikey;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_change_password);

        apikey = i.getStringExtra("apikey");
        Log.i("Sign Out Activity","" + apikey);

        nv = (NavigationView)findViewById(R.id.nvHostChangePassword);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutChangePassword);
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.close, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_pre_register):
                        i = new Intent(getApplicationContext(),HostPreRegister.class);
                        //i.putExtra("apikey",apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_cancel_pre_register):
                        i= new Intent(getApplicationContext(),HostCancelPreRegister.class);
                        //i.putExtra("apikey",apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_change_password):
                        i= new Intent(getApplicationContext(),HostChangePassword.class);
                        //i.putExtra("apikey",apikey);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    } //push
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
