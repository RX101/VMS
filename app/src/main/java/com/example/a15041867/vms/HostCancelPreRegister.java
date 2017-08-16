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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HostCancelPreRegister extends AppCompatActivity {

    ArrayList<Visitor> alVisitor = new ArrayList<Visitor>();
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private VisitorArrayAdapter arrayAdapter;
    Intent i,intentAPI;
    ListView lv;
    String apikey;
    Visitor visitor;
    String useremail;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_cancel_pre_register);

        i = getIntent();
        useremail = i.getStringExtra("user_email");

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
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",useremail);
                        startActivity(i);
                        break;
                    case (R.id.nav_change_password):
                        i = new Intent(getApplicationContext(), HostChangePassword.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",useremail);
                        startActivity(i);
                        break;
                    case (R.id.log_out):
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",useremail);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("apikey");
        useremail = intentAPI.getStringExtra("user_email");
        alVisitor.clear();
        arrayAdapter.notifyDataSetChanged();

        Log.i("Sign In Activity","" + apikey);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            intentAPI = getIntent();
            apikey = intentAPI.getStringExtra("apikey");
            useremail = intentAPI.getStringExtra("user_email");
            if (apikey != null) {


                HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/getVisitorInfoByUserEmail.php");
                request.setMethod("POST");
                request.addData("apikey", apikey);
                request.addData("user_email",useremail);
                    request.execute();
                try {
                        String jsonString = request.getResponse();
                        JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Visitor visitor = new Visitor();
                        visitor.setVisitor_name(jObj.getString("visitor_name"));
                        visitor.setVisitor_phone_number(jObj.getString("handphone_number"));
                        visitor.setVisitor_email(jObj.getString("visitor_email"));
                        visitor.setDate_in(jObj.getString("date_in"));
                        visitor.setTime_in(jObj.getString("time_in"));
                        alVisitor.add(visitor);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arrayAdapter = new VisitorArrayAdapter(this, R.layout.row_visitor_info, alVisitor);
                lv.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View arg1, final int position, long arg3) {
                            final Visitor todelete = alVisitor.get(position);
                            AlertDialog.Builder alertdialog = new AlertDialog.Builder(
                                    HostCancelPreRegister.this);
                            alertdialog.setTitle("Selected Visitor \n" + todelete.getVisitor_name());
                            alertdialog.setMessage("" + todelete.getVisitor_email());
                            alertdialog.setNegativeButton("Cancel", null);
                            alertdialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/deleteVisitor.php");
                                    request.setMethod("POST");
                                    request.addData("visitor_email", todelete.getVisitor_email());
                                    request.execute();
                                    try {
                                        HttpRequest request1 = new HttpRequest("http://ruixian-ang97.000webhostapp.com/deleteVisitorInfo.php");
                                        request1.setMethod("POST");
                                        request1.addData("visitor_email", todelete.getVisitor_email());
                                        request1.execute();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    alVisitor.remove(alVisitor.get(position));
                                    arrayAdapter.notifyDataSetChanged();
                                    Toast.makeText(HostCancelPreRegister.this, "Visitor Deleted " + todelete.getVisitor_email(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            alertdialog.show();
                        }

                    });
            } else {
                Toast.makeText(HostCancelPreRegister.this, "API is null", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

