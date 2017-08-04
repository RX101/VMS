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
    Intent i,intentAPI;
    ListView lv;
    String apikey, db_host_email;
    Visitor visitor;
    User usermail;
    Boolean found = false;
    Boolean host_found = false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("apikey");
        Log.i("Sign In Activity","" + apikey);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_cancel_pre_register);

        i = getIntent();
        String useremail = i.getStringExtra("user_email");
        //i.putExtra("useremail",useremail);
        Toast.makeText(HostCancelPreRegister.this,useremail,Toast.LENGTH_SHORT).show();

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
                        startActivity(i);
                        break;
                    case (R.id.nav_cancel_pre_register):
                        i = new Intent(getApplicationContext(), HostCancelPreRegister.class);
                        i.putExtra("apikey",apikey);
                        startActivity(i);
                        break;
                    case (R.id.nav_change_password):
                        i = new Intent(getApplicationContext(), HostChangePassword.class);
                        i.putExtra("apikey",apikey);
                        startActivity(i);
                        break;
                    case (R.id.log_out):
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("apikey",apikey);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            intentAPI = getIntent();
           apikey = intentAPI.getStringExtra("apikey");
          //  useremail = i.getStringExtra("user_email");
       //    if(useremail==user.getUser_email()) {
//               HttpRequest request3 = new HttpRequest("http//ruixian-ang97.000webhostapp.com/getUserByEmail.php");
//               request3.setMethod("POST");
//               request3.addData("apikey",apikey);
//               request3.addData("user_email",useremail);
//               request3.execute();
//               try {
//                   String jsonString = request3.getResponse();
//                   JSONObject jsonObject = new JSONObject(jsonString);
//                   User user = new User();
//                   user.setUser_email(jsonObject.getString("user_email"));
//
//               } catch (Exception e) {
//                   e.printStackTrace();
//               }


               if (apikey != null) {
                   host_found = false;

                   HttpRequest requestHostEmail = new HttpRequest("https://ruixian-ang97.000webhostapp.com/getUser.php");
                   requestHostEmail.setMethod("POST");
                   requestHostEmail.addData("apikey",apikey);
                   requestHostEmail.execute();
                   try {
                       String jsonStringUser = requestHostEmail.getResponse();
                       JSONArray jsonArrayUser = new JSONArray(jsonStringUser);
                       for(int i = 0; i<jsonArrayUser.length(); i++){
                           JSONObject jsonObject = (JSONObject) jsonArrayUser.get(i);
                           db_host_email = jsonObject.getString("user_email");

                           if(useremail.equalsIgnoreCase(db_host_email)){
                               host_found = true;

                           }
                       }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   if(host_found==true) {

                       HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/getVisitorInfo.php");
                       request.setMethod("POST");
                       request.addData("apikey", apikey);
                       request.execute();
//                   try {
//                       String jsonString = request.getResponse();
//                       JSONArray jsonArray = new JSONArray(jsonString);
//                       for(int i =0; i<jsonArray.length(); i++){
//                           JSONObject jsonObject = jsonArray.getJSONObject(i);
//                           int date_out = Integer.parseInt(jsonObject.getString("date_out"));
//                           int time_out = Integer.parseInt(jsonObject.getString("time_out"));
//                           int isSignIn = Integer.parseInt(jsonObject.getString("isSignIn"));
//                           if(isSignIn ==0 && date_out == 0 && time_out==0){
//                               found = true;
//                           }
//                       }
//                   } catch (Exception e) {
//                       e.printStackTrace();
//                   }
//                User user = new User();
//                String HostEmail = i.getStringExtra("user_email");
//                if(user.getUser_email()==HostEmail) {

                       try {
                           String jsonString = request.getResponse();
                           JSONArray jsonArray = new JSONArray(jsonString);
                           // Populate the arraylist personList
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
//                       HttpRequest request1 = new HttpRequest("http://ruixian-ang97.000webhostapp.com/getSignInVisitor.php");
//                       request1.setMethod("POST");
//                       request1.addData("apikey", apikey);
//                       String jsonString1 = request1.getResponse();
//                       JSONArray jsonArray1 = new JSONArray(jsonString1);
//                       for (int o = 0; o < jsonArray1.length(); o++) {
//                           JSONObject jObj1 = jsonArray.getJSONObject(o);
//                           Visitor visitor1 = new Visitor();
//
//                           request1.execute();
//                       }
                       } catch (Exception e) {
                           e.printStackTrace();
                       } //push


                       final VisitorArrayAdapter arrayAdapter = new VisitorArrayAdapter(this, R.layout.row_visitor_info, alVisitor);
                       lv.setAdapter(arrayAdapter);
                       arrayAdapter.notifyDataSetChanged();
                       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                           @Override
                           public void onItemClick(AdapterView<?> parent, View arg1, final int position, long arg3) {
                               final Visitor todelete = alVisitor.get(position);
                               AlertDialog.Builder alertdialog = new AlertDialog.Builder(
                                       HostCancelPreRegister.this);
                               alertdialog.setTitle("Selected Visitor \n" + todelete);
                               alertdialog.setMessage("" + todelete);
                               alertdialog.setPositiveButton("Cancel", null);
                               alertdialog.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int id) {
                                       HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/deleteVisitor.php");
                                       request.setMethod("POST");
                                       request.addData("visitor_email", todelete.getVisitor_email());
                                       request.execute();

                                       /******************************/
                                       try {
                                           HttpRequest request1 = new HttpRequest("http://ruixian-ang97.000webhostapp.com/deleteVisitorInfo.php");
                                           request1.setMethod("POST");
                                           request1.addData("visitor_email", todelete.getVisitor_email());
                                           request1.execute();
                                           //String jsonString = request.getResponse();
                                           //finish();
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
                   }

               } else {
                   Toast.makeText(HostCancelPreRegister.this, "API is null", Toast.LENGTH_LONG).show();
               }

         //  }
        }



    }

}
