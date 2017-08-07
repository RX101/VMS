package com.example.a15041867.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class SummaryInfoActivity extends AppCompatActivity {
    private static final String TAG = "SummaryInfoActivity";
    Intent i;
    String apikey, startDate, endDate, selectedSum, result;
    TextView tvSum1, tvSum2, tvSum3, tvSum4, tvSum5, tvSum6, tvSum7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_info);

        tvSum1 = (TextView)findViewById(R.id.tvSum1);
        tvSum2 = (TextView)findViewById(R.id.tvSum2);
        tvSum3 = (TextView)findViewById(R.id.tvSum3);
        tvSum4 = (TextView)findViewById(R.id.tvSum4);
        tvSum5 = (TextView)findViewById(R.id.tvSum5);
        tvSum6 = (TextView)findViewById(R.id.tvSum6);
        tvSum7 = (TextView)findViewById(R.id.tvSum7);

        i = getIntent();
        apikey = i.getStringExtra("api");
        startDate = i.getStringExtra("startDate");
        endDate = i.getStringExtra("endDate");
        selectedSum = i.getStringExtra("selectedSum");

        if(selectedSum.equalsIgnoreCase("no of visitors")){
            HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getNumberOfVisitors.php");
            request.setMethod("POST");
            request.addData("apikey", apikey);
            request.addData("start_date", "'" + startDate + "'");
            request.addData("end_date", "'" + endDate + "'");
            request.execute();

            try {
                String jsonString = request.getResponse();
                Log.d(TAG, "jsonString: " + jsonString);

                JSONArray jsonArray = new JSONArray(jsonString);

                // Populate the arraylist personList
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    result = jObj.getString("COUNT(visitor_email)");
                }
                tvSum1.setText("Number of visitors from");
                tvSum2.setText(startDate);
                tvSum3.setText("to");
                tvSum4.setText(endDate);
                tvSum5.setText("is");
                tvSum6.setText(result);
                tvSum7.setText(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }else if(selectedSum.equalsIgnoreCase("busiest time span")){

            HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getBusiestTimeSpan.php");
            request.setMethod("POST");
            request.addData("apikey", apikey);
            request.addData("start_date", "'" + startDate + "'");
            request.addData("end_date", "'" + endDate + "'");
            request.execute();

            try {
                String jsonString = request.getResponse();
                Log.d(TAG, "jsonString: " + jsonString);

                JSONArray jsonArray = new JSONArray(jsonString);

                // Populate the arraylist personList
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObj = jsonArray.getJSONObject(i);
                    String count =  jObj.getString("count(time_in)");
                    String hour = jObj.getString("HOUR(time_in)");
                    tvSum1.setText("Busiest time during");
                    tvSum2.setText(startDate);
                    tvSum3.setText("to");
                    tvSum4.setText(endDate);
                    tvSum5.setText("  is  " + hour + ":00 hour");
                    tvSum6.setText("No of visitors during the period is");
                    tvSum7.setText(count);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
