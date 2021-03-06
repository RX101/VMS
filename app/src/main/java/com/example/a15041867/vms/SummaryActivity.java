package com.example.a15041867.vms;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {

    private static final String TAG = "SummaryActivity";

    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;


    Intent i;
    String apikey;

    Button btnSubmit, btnReset;
    RadioGroup rgSummary;
    EditText etStartDate, etEndDate;
    Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        i = getIntent();
        apikey = i.getStringExtra("api");

        session = new Session(this);
//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView) findViewById(R.id.nvSummary);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutSummary);
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
                    case (R.id.nav_evacuation):
                        i = new Intent(getApplicationContext(), EvacuationActivity.class);
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

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnReset = (Button) findViewById(R.id.btnReset);
        etStartDate = (EditText) findViewById(R.id.etStartDate);
        etEndDate = (EditText) findViewById(R.id.etEndDate);
        rgSummary = (RadioGroup) findViewById(R.id.rgSummary);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                mDatePicker = new DatePickerDialog(SummaryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        etStartDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate((System.currentTimeMillis()));
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                final int mYear = mcurrentDate.get(Calendar.YEAR);
                final int mMonth = mcurrentDate.get(Calendar.MONTH);
                final int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker2;
                mDatePicker2 = new DatePickerDialog(SummaryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */

                        selectedmonth = selectedmonth + 1;
                        etEndDate.setText(selectedyear + "-" + selectedmonth + "-" + selectedday);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker2.getDatePicker().setMaxDate((System.currentTimeMillis()));
                mDatePicker2.setTitle("Select Date");
                mDatePicker2.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SummaryActivity.this, "submit button clicked", Toast.LENGTH_LONG).show();
                int selectedButtonId = rgSummary.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(selectedButtonId);
                String selectedPosition = rb.getText().toString();

                if(selectedPosition.equalsIgnoreCase("number of visitors")){
                    //Toast.makeText(SummaryActivity.this, selectedPosition, Toast.LENGTH_LONG).show();
                    i = new Intent(getApplicationContext(), SummaryInfoActivity.class);
                    i.putExtra("api", apikey);
                    i.putExtra("startDate", etStartDate.getText().toString());
                    i.putExtra("endDate", etEndDate.getText().toString());
                    i.putExtra("selectedSum", "no of visitors");
                    startActivity(i);

                }else if(selectedPosition.equalsIgnoreCase("busiest time span")){
                    //Toast.makeText(SummaryActivity.this, selectedPosition, Toast.LENGTH_LONG).show();
                    i = new Intent(getApplicationContext(), SummaryInfoActivity.class);
                    i.putExtra("api", apikey);
                    i.putExtra("startDate", etStartDate.getText().toString());
                    i.putExtra("endDate", etEndDate.getText().toString());
                    i.putExtra("selectedSum", "busiest time span");
                    startActivity(i);

                }else if(selectedPosition.equalsIgnoreCase("frequency of visitors")){

                    i = new Intent(getApplicationContext(), VisitorFrequencyActivity.class);
                    i.putExtra("api", apikey);
                    i.putExtra("startDate", etStartDate.getText().toString());
                    // Toast.makeText(SummaryActivity.this, etStartDate.getText().toString(), Toast.LENGTH_LONG).show();
                    i.putExtra("endDate", etEndDate.getText().toString());
                    startActivity(i);

                }else if(selectedPosition.equalsIgnoreCase("Details of visitors based on time span")){
                    i = new Intent(getApplicationContext(), VisitorInfoByDateActivity.class);
                    i.putExtra("api", apikey);
                    i.putExtra("startDate", etStartDate.getText().toString());
                   // Toast.makeText(SummaryActivity.this, etStartDate.getText().toString(), Toast.LENGTH_LONG).show();
                    i.putExtra("endDate", etEndDate.getText().toString());
                    startActivity(i);
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etStartDate.setText(" ");
                etEndDate.setText(" ");
                rgSummary.clearCheck();
            }
        });
    }

        // Get the Id of the selected radio button in the RadioGroup



    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etStartDate.setText(sdf.format(myCalendar.getTime()));
        etEndDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
