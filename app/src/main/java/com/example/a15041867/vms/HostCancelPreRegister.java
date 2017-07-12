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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HostCancelPreRegister extends AppCompatActivity {

    ArrayList<Visitor> alVisitor = new ArrayList<Visitor>();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    Intent i;
    ListView lv;
    String apikey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_cancel_pre_register);

        lv = (ListView) findViewById(R.id.lvCancel);
        nv = (NavigationView) findViewById(R.id.nvCancelPreRegister);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutCancelPreRegister);
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
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {


            i = getIntent();
           apikey = i.getStringExtra("apikey");

            if (apikey != null) {
                HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/getVisitor.php");
                request.setMethod("POST");
                request.addData("apikey", apikey);
                request.execute();
                try {
                    String jsonString = request.getResponse();

                    JSONArray jsonArray = new JSONArray(jsonString);

                    // Populate the arraylist personList
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Visitor visitor = new Visitor();
                        visitor.setVisitor_email(jObj.getString("visitor_email"));
                        visitor.setVisitor_name(jObj.getString("visitor_name"));
                        visitor.setVisitor_phone_number(jObj.getString("handphone_number"));
                        alVisitor.add(visitor);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                final VisitorArrayAdapter arrayAdapter = new VisitorArrayAdapter(this, R.layout.row_visitor_info, alVisitor);
                lv.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View arg1, final int position, long arg3) {

                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(
                                HostCancelPreRegister.this);
                        alertdialog.setTitle("Selected Visitor");
                        alertdialog.setMessage(""+parent.getItemAtPosition(position));
                        alertdialog.setPositiveButton("Cancel", null);
                        alertdialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/deleteVisitor.php");
                                request.setMethod("POST");
                                request.addData("user_email","user_email");
                                request.execute();

                                /******************************/
                                try{
                                    String jsonString = request.getResponse();
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                alVisitor.remove(alVisitor.get(position));
                                arrayAdapter.notifyDataSetChanged();
                                Toast.makeText(HostCancelPreRegister.this,"Visitor Deleted",Toast.LENGTH_SHORT).show();
                                arrayAdapter.notifyDataSetChanged();
                            }
                        });
                        alertdialog.show();
                    }
                });

           }
        }
    }
}
