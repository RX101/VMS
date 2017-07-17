package com.example.a15041867.vms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserInfoActivity extends AppCompatActivity {

    private static final String TAG = "UserInfoActivity";

    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    Intent i;
    String loginId, apikey;

    ListView lv;
    ArrayList<User> userList = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        session = new Session(this);
//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView) findViewById(R.id.nvManager);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutUserInfo);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.nav_visitor_info):
                        i = new Intent(getApplicationContext(), VisitorInfoActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_summary):
                        i = new Intent(getApplicationContext(), SummaryActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_evacuation):
                        i = new Intent(getApplicationContext(), EvacuationActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_add_user):
                        i = new Intent(getApplicationContext(), AddUserActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.nav_edit_user):
                        i = new Intent(getApplicationContext(), UserInfoActivity.class);
                        startActivity(i);
                        break;
                    case (R.id.log_out):
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });


        userList.clear();
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //TODO 03 Extract the loginId and API Key from the intent object
            i = getIntent();
            loginId = i.getStringExtra("loginId");
            apikey = i.getStringExtra("apikey");
            /******************************/
            if (apikey != null) {
                HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/getUser.php");
                request.setMethod("POST");
                request.addData("loginId", loginId);
                request.addData("apikey", apikey);
                request.execute();

                try {
                    String jsonString = request.getResponse();
                    Log.d(TAG, "jsonString: " + jsonString);

                    JSONArray jsonArray = new JSONArray(jsonString);

                    // Populate the arraylist personList
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        User user = new User();
                        user.setUser_email(jObj.getString("user_email"));
                        user.setName(jObj.getString("name"));
                        user.setHandphone_number(jObj.getString("handphone_number"));
                        user.setPosition(jObj.getString("position"));
                        user.setBlock(jObj.getString("block"));
                        user.setUnit(jObj.getString("unit"));
                        user.setPassword(jObj.getString("password"));
                        userList.add(user);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                UserArrayAdapter arrayAdapter = new UserArrayAdapter(this, R.layout.row_user, userList);
                lv = (ListView) findViewById(R.id.lvUserInfo);
                lv.setAdapter(arrayAdapter);

            } else {
                // AlertBox
                showAlert("Login Failed");
            }
        } else {
            // AlertBox
            showAlert("No network connection!");
        }
    }

    private void showAlert(String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        UserInfoActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }
}



