package com.example.a15041867.vms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VisitorInfoByDateActivity extends AppCompatActivity {
    private static final String TAG = "VisitorInfoByDateActivity";

    Intent i;

    String apikey, startDate, endDate;
    ListView lv;
    ArrayList<Visitor> visitorList = new ArrayList<Visitor>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_info_by_date);

        lv = (ListView)findViewById(R.id.lvVisInfoDate);

        visitorList.clear();
        // Check if there is network access
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            //TODO 03 Extract the loginId and API Key from the intent object
            i = getIntent();
            apikey = i.getStringExtra("api");
            startDate = i.getStringExtra("startDate");
            endDate = i.getStringExtra("endDate");
            //Toast.makeText(getBaseContext(),startDate +  "\n" + endDate,Toast.LENGTH_LONG).show();

            /******************************/
            if (apikey != null) {
                HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitorsByDate.php");
                request.setMethod("POST");
                request.addData("apikey", apikey);
                request.addData("start_date", "'" + startDate + "'");
                request.addData("end_date", "'" + endDate + "'");
                request.execute();

                try {
                    String jsonString = request.getResponse();
                    Log.i("testing", jsonString);
                    JSONArray jsonArray = new JSONArray(jsonString);

                    // Populate the arraylist personList
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        Visitor visitor = new Visitor();
                        visitor.setVisitor_email(jObj.getString("visitor_email"));
                        visitor.setSub_visitors(jObj.getString("sub_visitor"));
                        visitor.setUser_email(jObj.getString("user_email"));
                        visitorList.add(visitor);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                VisitorInfoDateArrayAdapter arrayAdapter = new VisitorInfoDateArrayAdapter(this, R.layout.row_visitor_info_date, visitorList);
                lv = (ListView)findViewById(R.id.lvVisInfoDate);
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
                        VisitorInfoByDateActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }
}
