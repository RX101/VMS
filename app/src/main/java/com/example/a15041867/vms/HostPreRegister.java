package com.example.a15041867.vms;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import static android.R.id.message;


public class HostPreRegister extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    TextView tvSubVisitor;
    Intent i, intentAPI;
    String db_visitor_email, db_host_email;
    EditText etNumber, etName, etEmail, etTime, etDate, etHostEmail, etSignInEmail, etSignInVisitUnit, etSignInVisitBlock, etSv2Sub1, etSv2Sub2,
            etSv3Sub1, etSv3Sub2, etSv3Sub3, etSv4Sub1, etSv4Sub2, etSv4Sub3, etSv4Sub4,
            etSv5Sub1, etSv5Sub2, etSv5Sub3, etSv5Sub4, etSv5Sub5, TESTINGONLY;
    Spinner spnNumVisitor;
    Button btnSubmit, buttonGenerate;
    ImageView iv;
    Boolean visitor_found, host_found;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private String block, unit, apikey, sub_visitor, userEmail;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_pre_register);

        tvSubVisitor = (TextView) findViewById(R.id.textViewSubVisitor);
        spnNumVisitor = (Spinner) findViewById(R.id.spinnerNumVisitor);
        etTime = (EditText) findViewById(R.id.editTextTime);
        etDate = (EditText) findViewById(R.id.editTextDate);
        buttonGenerate = (Button) findViewById(R.id.buttonGeneragte);
        TESTINGONLY = (EditText) findViewById(R.id.TESTINGONLY);

        final ImageView iv = (ImageView) findViewById(R.id.imageView);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("apikey");
        userEmail = intentAPI.getStringExtra("user_email");
        i = getIntent();
//        String useremail = i.getStringExtra("useremail");
        block = i.getStringExtra("block");
        unit = i.getStringExtra("unit");

        nv = (NavigationView) findViewById(R.id.nvPreRegister);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutPreRegister);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        apikey = intentAPI.getStringExtra("apikey");

        if (ContextCompat.checkSelfPermission(HostPreRegister.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(HostPreRegister.this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(HostPreRegister.this, new String[]{Manifest.permission.SEND_SMS}, 1);

            } else {
                ActivityCompat.requestPermissions(HostPreRegister.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        } else {

        }
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
//                    case (R.id.nav_pre_register):
//                        i = new Intent(getApplicationContext(), HostPreRegister.class);
//                        i.putExtra("apikey", apikey);
//                        startActivity(i);
//                        break;
                    case (R.id.nav_cancel_pre_register):
                        i = new Intent(getApplicationContext(), HostCancelPreRegister.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case (R.id.nav_change_password):
                        i = new Intent(getApplicationContext(), HostChangePassword.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case (R.id.log_out):
                        i = new Intent(getApplicationContext(), MainActivity.class);
                        i.putExtra("apikey",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(String.valueOf(TESTINGONLY), BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    iv.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                final java.util.Calendar mcurrentDate = java.util.Calendar.getInstance();
                int mYear = mcurrentDate.get(java.util.Calendar.YEAR);
                int mMonth = mcurrentDate.get(java.util.Calendar.MONTH);
                final int mDay = mcurrentDate.get(java.util.Calendar.DAY_OF_MONTH);

                //set today as the minimum date

                final DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(HostPreRegister.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        if (mDay <= selectedday) {
                            etDate.setText("" + selectedyear + "/" + selectedmonth + "/" + selectedday);
                        } else {
                            Toast.makeText(HostPreRegister.this, "Please select a valid date", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, mYear, mMonth, mDay);

                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }

        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(HostPreRegister.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        etTime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
                etName = (EditText) findViewById(R.id.editTextName);
                etEmail = (EditText) findViewById(R.id.editTextEmail);
                etTime = (EditText) findViewById(R.id.editTextTime);
                etDate = (EditText) findViewById(R.id.editTextDate);
                tvSubVisitor = (TextView) findViewById(R.id.textViewSubVisitor);
                String number = etNumber.getText().toString();
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String time = etTime.getText().toString();
                String date = etDate.getText().toString();

                sub_visitor = tvSubVisitor.getText().toString();

                if (name.equals("")) {
                    etName.setError("Name field is empty");
                } else if (number.equals("")) {
                    etNumber.setError("This field is empty");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.setError("Invalid Email, Please try again.");
                } else if (date.equals("")) {
                    etDate.setError("Time field is empty");
                } else if (time.equals("")) {
                    etTime.setError("Date field is empty");
                } else {
                    HttpRequest requestVisitorEmail = new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php");
                    requestVisitorEmail.setMethod("POST");
                    requestVisitorEmail.addData("apikey", apikey);
                    requestVisitorEmail.execute();
                    visitor_found = false;
                    try {
                        String jsonString = requestVisitorEmail.getResponse();
//                        Log.i("response", jsonString);
                        JSONArray jsonArray = new JSONArray(jsonString);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                            db_visitor_email = jsonObj.getString("visitor_email");
                            if (email.equalsIgnoreCase(db_visitor_email)) {
                                visitor_found = true;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(visitor_found==false) {
                        HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/insertVisitor.php");
                        request.setMethod("POST");
                        request.addData("visitor_email", etEmail.getText().toString());
                        request.addData("visitor_name", etName.getText().toString());
                        request.addData("handphone_number", etNumber.getText().toString());
                        request.execute();
                        try {
                            HttpRequest request1 = new HttpRequest("http://ruixian-ang97.000webhostapp.com/insertVisitorInfo.php");
                            request1.setMethod("POST");
                            request1.addData("user_email",userEmail);
                            request1.addData("visitor_email", etEmail.getText().toString());
                            request1.addData("date_in", etDate.getText().toString());
                            request1.addData("time_in", etTime.getText().toString());
                            request1.addData("sub_visitor", sub_visitor);
                            Toast.makeText(HostPreRegister.this, "Visitor inserted and a QR code has been sent to visitor", Toast.LENGTH_SHORT).show();
                            request1.execute();
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            BitMatrix bitMatrix = null;
                            try {
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                bitMatrix = multiFormatWriter.encode(number + "\n" + name + "\n" + email + "\n" + time + "\n" + date, BarcodeFormat.QR_CODE, 200, 200);
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(number, null, "Qr code: "+bitmap, null, null);
                                finish();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    //                    else{
//                        Toast.makeText(getApplicationContext(), "Error occurred.",
//                                Toast.LENGTH_LONG).show();
//                    }
                }
            }
        });

        spnNumVisitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spnNumVisitor.getSelectedItemPosition() == 1) {
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor2, null);

                    //Obtain th UI component in the input.xml layout
                    etSv2Sub1 = (EditText) viewDialog.findViewById(R.id.editTextSv2Sub1);
                    etSv2Sub2 = (EditText) viewDialog.findViewById(R.id.editTextSv2Sub2);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(HostPreRegister.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv2Sub1 = etSv2Sub1.getText().toString();
                            String sv2Sub2 = etSv2Sub2.getText().toString();

                            //Update the Text entered by the user
                            if (etSv2Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 1", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv2Sub2.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 2", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else {
                                tvSubVisitor.setText(sv2Sub1 + "," + sv2Sub2);
                            }

                        }
                    });
                    myBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            spnNumVisitor.setSelection(0);
                        }
                    });

                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                } else if (spnNumVisitor.getSelectedItemPosition() == 2) {
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor3, null);

                    //Obtain th UI component in the input.xml layout
                    etSv3Sub1 = (EditText) viewDialog.findViewById(R.id.editTextSv3Sub1);
                    etSv3Sub2 = (EditText) viewDialog.findViewById(R.id.editTextSv3Sub2);
                    etSv3Sub3 = (EditText) viewDialog.findViewById(R.id.editTextSv3Sub3);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(HostPreRegister.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv3Sub1 = etSv3Sub1.getText().toString();
                            String sv3Sub2 = etSv3Sub2.getText().toString();
                            String sv3Sub3 = etSv3Sub3.getText().toString();

                            //Update the Text entered by the user
                            if (etSv3Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 1", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv3Sub2.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 2", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv3Sub3.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 3", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else {
                                tvSubVisitor.setText(sv3Sub1 + "," + sv3Sub2 + "," + sv3Sub3);
                            }

                        }
                    });
                    myBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            spnNumVisitor.setSelection(0);
                        }
                    });

                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                } else if (spnNumVisitor.getSelectedItemPosition() == 3) {
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor4, null);

                    //Obtain th UI component in the input.xml layout
                    etSv4Sub1 = (EditText) viewDialog.findViewById(R.id.editTextSv4Sub1);
                    etSv4Sub2 = (EditText) viewDialog.findViewById(R.id.editTextSv4Sub2);
                    etSv4Sub3 = (EditText) viewDialog.findViewById(R.id.editTextSv4Sub3);
                    etSv4Sub4 = (EditText) viewDialog.findViewById(R.id.editTextSv4Sub4);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(HostPreRegister.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv4Sub1 = etSv4Sub1.getText().toString();
                            String sv4Sub2 = etSv4Sub2.getText().toString();
                            String sv4Sub3 = etSv4Sub3.getText().toString();
                            String sv4Sub4 = etSv4Sub4.getText().toString();

                            //Update the Text entered by the user
                            if (etSv4Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 1", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv4Sub2.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 2", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv4Sub3.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 3", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv4Sub4.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 4", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else {
                                tvSubVisitor.setText(sv4Sub1 + "," + sv4Sub2 + "," + sv4Sub3 + "," + sv4Sub4);
                            }

                        }
                    });
                    myBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            spnNumVisitor.setSelection(0);
                        }
                    });

                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                } else if (spnNumVisitor.getSelectedItemPosition() == 4) {
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor5, null);

                    //Obtain th UI component in the input.xml layout
                    etSv5Sub1 = (EditText) viewDialog.findViewById(R.id.editTextSv5Sub1);
                    etSv5Sub2 = (EditText) viewDialog.findViewById(R.id.editTextSv5Sub2);
                    etSv5Sub3 = (EditText) viewDialog.findViewById(R.id.editTextSv5Sub3);
                    etSv5Sub4 = (EditText) viewDialog.findViewById(R.id.editTextSv5Sub4);
                    etSv5Sub5 = (EditText) viewDialog.findViewById(R.id.editTextSv5Sub5);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(HostPreRegister.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv5Sub1 = etSv5Sub1.getText().toString();
                            String sv5Sub2 = etSv5Sub2.getText().toString();
                            String sv5Sub3 = etSv5Sub3.getText().toString();
                            String sv5Sub4 = etSv5Sub4.getText().toString();
                            String sv5Sub5 = etSv5Sub5.getText().toString();

                            //Update the Text entered by the user
                            if (etSv5Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 1", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv5Sub2.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 2", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv5Sub3.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 3", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv5Sub4.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 4", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else if (etSv5Sub5.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(), "Please enter sub visitor 5", Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else {
                                tvSubVisitor.setText(sv5Sub1 + "," + sv5Sub2 + "," + sv5Sub3 + "," + sv5Sub4 + "," + sv5Sub5);
                            }

                        }
                    });
                    myBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            spnNumVisitor.setSelection(0);
                        }
                    });

                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }
    //yo


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(HostPreRegister.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(HostPreRegister.this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                }
        }
    }

    protected void onStart() {
        super.onStart();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
//    protected void sendSMSMessage() {
//
//        if (ContextCompat.checkSelfPermission(this,
//                Manifest.permission.SEND_SMS)
//                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.SEND_SMS)) {
//                etNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
//                etName = (EditText) findViewById(R.id.editTextName);
//                etEmail = (EditText) findViewById(R.id.editTextEmail);
//                etTime = (EditText) findViewById(R.id.editTextTime);
//                etDate = (EditText) findViewById(R.id.editTextDate);
//                etHostEmail = (EditText) findViewById(R.id.editTextHostEmail);
//                tvSubVisitor = (TextView) findViewById(R.id.textViewSubVisitor);
//                String number=etNumber.getText().toString();
//                String name =etName.getText().toString();
//                String email = etEmail.getText().toString();
//                String time = etTime.getText().toString();
//                String date = etDate.getText().toString();
//                String hostEmail = etHostEmail.getText().toString();
//                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
//                try{
//                    BitMatrix bitMatrix = multiFormatWriter.encode(number+"\n"+name+"\n"+email+"\n"+time+"\n"+date+"\n"+hostEmail, BarcodeFormat.QR_CODE,200,200);
//                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(number, null, String.valueOf(bitmap), null, null);
////                    iv.setImageBitmap(bitmap);
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
//                } catch(WriterException e){
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "SMS NOT NOT NOT sent.",
//                            Toast.LENGTH_LONG).show();
//                }
//            } else {
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.SEND_SMS},
//                        MY_PERMISSIONS_REQUEST_SEND_SMS);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            }
//        }
//
//    }

}
