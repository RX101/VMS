package com.example.a15041867.vms;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class UserInfoActivity extends AppCompatActivity {

    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        session = new Session(this);
//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvManager);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutUserInfo);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_visitor_info):
                        i = new Intent(getApplicationContext(),VisitorInfoActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_summary):
                        i= new Intent(getApplicationContext(),SummaryActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_evacuation):
                        i= new Intent(getApplicationContext(),EvacuationActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_add_user):
                        i= new Intent(getApplicationContext(),AddUserActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_edit_user):
                        i= new Intent(getApplicationContext(),UserInfoActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.log_out):
                        i= new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }
}
