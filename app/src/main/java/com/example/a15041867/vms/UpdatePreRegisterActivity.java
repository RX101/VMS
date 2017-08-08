//package com.example.a15041867.vms;
//
//import android.Manifest;
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.support.design.widget.NavigationView;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import java.util.Calendar;
//
//public class UpdatePreRegisterActivity extends AppCompatActivity {
//
//    TextView tvUpdateSubVisitor;
//    Intent i, intentAPI;
//    private TextView tvSubVisitor;
//    private Intent i, intentAPI;
//    private String db_visitor_email, db_host_email;
//    private EditText etNumber, etName, etEmail, etTime, etDate, etHostEmail, etSignInEmail, etSignInVisitUnit, etSignInVisitBlock, etSv2Sub1, etSv2Sub2,
//            etSv3Sub1, etSv3Sub2, etSv3Sub3, etSv4Sub1, etSv4Sub2, etSv4Sub3, etSv4Sub4,
//            etSv5Sub1, etSv5Sub2, etSv5Sub3, etSv5Sub4, etSv5Sub5, TESTINGONLY;
//    private Spinner spnNumVisitor;
//    private Button btnUpdate, buttonGenerate;
//    private ImageView iv;
//    private Boolean visitor_found, host_found;
//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mToggle;
//    private NavigationView nv;
//    private String block, unit, apikey, sub_visitor, userEmail;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_update_pre_register);
//
//        tvSubVisitor = (TextView) findViewById(R.id.textViewUpdateSubVisitor);
//        spnNumVisitor = (Spinner) findViewById(R.id.spinnerUpdateNumVisitor);
//        etTime = (EditText) findViewById(R.id.editTextUpdateTime);
//        etDate = (EditText) findViewById(R.id.editTextUpdateDate);
//        btnUpdate = (Button) findViewById(R.id.buttonUpdateSubmit);
//
//        intentAPI = getIntent();
//        apikey = intentAPI.getStringExtra("apikey");
//        userEmail = intentAPI.getStringExtra("user_email");
//
//        nv = (NavigationView) findViewById(R.id.nvPreRegister);
//
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutPreRegister);
//        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
//
//        mDrawerLayout.addDrawerListener(mToggle);
//        mToggle.syncState();
//
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        apikey = intentAPI.getStringExtra("apikey");
//
//
//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
////                    case (R.id.nav_pre_register):
////                        i = new Intent(getApplicationContext(), HostPreRegister.class);
////                        i.putExtra("apikey", apikey);
////                        startActivity(i);
////                        break;
//                    case (R.id.nav_cancel_pre_register):
//                        i = new Intent(getApplicationContext(), HostCancelPreRegister.class);
//                        i.putExtra("apikey",apikey);
//                        i.putExtra("user_email",userEmail);
//                        startActivity(i);
//                        break;
//                    case (R.id.nav_change_password):
//                        i = new Intent(getApplicationContext(), HostChangePassword.class);
//                        i.putExtra("apikey",apikey);
//                        i.putExtra("user_email",userEmail);
//                        startActivity(i);
//                        break;
//                    case (R.id.log_out):
//                        i = new Intent(getApplicationContext(), MainActivity.class);
//                        i.putExtra("apikey",apikey);
//                        i.putExtra("user_email",userEmail);
//                        startActivity(i);
//                        break;
//                }
//                return true;
//            }
//        });
//
//        etDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //To show current date in the datepicker
//                final java.util.Calendar mcurrentDate = java.util.Calendar.getInstance();
//                int mYear = mcurrentDate.get(java.util.Calendar.YEAR);
//                int mMonth = mcurrentDate.get(java.util.Calendar.MONTH);
//                final int mDay = mcurrentDate.get(java.util.Calendar.DAY_OF_MONTH);
//
//                //set today as the minimum date
//
//                final DatePickerDialog mDatePicker;
//                mDatePicker = new DatePickerDialog(HostPreRegister.this, new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                        // TODO Auto-generated method stub
//                    /*      Your code   to get date and time    */
//                        selectedmonth = selectedmonth + 1;
//                        if (mDay <= selectedday) {
//                            etDate.setText("" + selectedyear + "/" + selectedmonth + "/" + selectedday);
//                        } else {
//                            Toast.makeText(getBaseContext(), "Please select a valid date", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }, mYear, mMonth, mDay);
//
//                mDatePicker.setTitle("Select Date");
//                mDatePicker.show();
//            }
//
//        });
//
//        etTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(getBaseContext(), new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        etTime.setText(selectedHour + ":" + selectedMinute);
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Select Time");
//                mTimePicker.show();
//            }
//        });
//
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                etNumber = (EditText) findViewById(R.id.editTextUpdatePhoneNumber);
//                etName = (EditText) findViewById(R.id.editTextUpdateName);
//                etEmail = (EditText) findViewById(R.id.editTextUpdateEmail);
//                etTime = (EditText) findViewById(R.id.editTextUpdateTime);
//                etDate = (EditText) findViewById(R.id.editTextUpdateDate);
//                tvSubVisitor = (TextView) findViewById(R.id.textViewUpdateSubVisitor);
//                String number = etNumber.getText().toString();
//                String name = etName.getText().toString();
//                String email = etEmail.getText().toString();
//                String time = etTime.getText().toString();
//                String date = etDate.getText().toString();
//
//
//            }
//        });
//    }
//}
