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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class EvacuationActivity extends AppCompatActivity {
    private static final String TAG = "EvacuationActivity";

    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;

    Intent i;
    String apikey;
    ListView lv;
    ArrayList<Visitor> evacuationList = new ArrayList<Visitor>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evacuation);

        session = new Session(this);
//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView) findViewById(R.id.nvEvacuation);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutEvacuation);
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
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case (R.id.nav_user_info):
                        i = new Intent(getApplicationContext(), UserInfoActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case (R.id.nav_summary):
                        i = new Intent(getApplicationContext(), SummaryActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case (R.id.nav_add_user):
                        i = new Intent(getApplicationContext(), AddUserActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case (R.id.log_out):
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        evacuationList.clear();
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //TODO 03 Extract the loginId and API Key from the intent object
            i = getIntent();
            apikey = i.getStringExtra("api");
            /******************************/
            if (apikey != null) {
                HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/getCurrentVisitors.php");
                request.setMethod("POST");
                request.addData("apikey", apikey);
                request.execute();

                try {
                    String jsonString = request.getResponse();
                    Log.d(TAG, "jsonString: " + jsonString);

                    JSONArray jsonArray = new JSONArray(jsonString);


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Visitor visitor = new Visitor();
                        visitor.setVisitor_email(jObj.getString("visitor_email"));
                        visitor.setUser_email(jObj.getString("user_email"));
                        visitor.setDate_in(jObj.getString("date_in"));
                        visitor.setTime_in(jObj.getString("time_in"));
                        visitor.setSub_visitors(jObj.getString("sub_visitor"));
                        evacuationList.add(visitor);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                EvacuationArrayAdapter arrayAdapter = new EvacuationArrayAdapter(this, R.layout.row_evacuation, evacuationList);
                lv = (ListView) findViewById(R.id.lvEvacuation);
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
                        EvacuationActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
