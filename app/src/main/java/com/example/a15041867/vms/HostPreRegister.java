package com.example.a15041867.vms;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;




public class HostPreRegister extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nv;
    private String block,unit, apikey;
    Intent i,intentAPI;
    EditText etNumber, etName, etEmail, etTime, etDate, etHostEmail,etSignInEmail, etSignInVisitUnit, etSignInVisitBlock, etSv2Sub1,etSv2Sub2,
            etSv3Sub1,etSv3Sub2, etSv3Sub3, etSv4Sub1, etSv4Sub2, etSv4Sub3, etSv4Sub4,
            etSv5Sub1, etSv5Sub2, etSv5Sub3, etSv5Sub4, etSv5Sub5;;
    Spinner spnNumVisitor;
    Button btnSubmit;
    TextView tvSubVisitor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_pre_register);

        spnNumVisitor = (Spinner) findViewById(R.id.spinnerNumVisitor);


        i = getIntent();
        block = i.getStringExtra("block");
        unit = i.getStringExtra("unit");

        nv = (NavigationView) findViewById(R.id.nvPreRegister);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayoutPreRegister);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("apikey");
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case (R.id.nav_pre_register):
                        i = new Intent(getApplicationContext(), HostPreRegister.class);
                        i.putExtra("apikey", apikey);
                        startActivity(i);
                        break;
                    case (R.id.nav_cancel_pre_register):
                        i = new Intent(getApplicationContext(), HostCancelPreRegister.class);
                        i.putExtra("apikey", apikey);
                        startActivity(i);
                        break;
                    case (R.id.nav_change_password):
                        i = new Intent(getApplicationContext(), HostChangePassword.class);
                        i.putExtra("apikey", apikey);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });

//        etDate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //To show current date in the datepicker
//                    java.util.Calendar mcurrentDate = java.util.Calendar.getInstance();
//                    int mYear = mcurrentDate.get(java.util.Calendar.YEAR);
//                    int mMonth = mcurrentDate.get(java.util.Calendar.MONTH);
//                    int mDay = mcurrentDate.get(java.util.Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog mDatePicker;
//                    mDatePicker = new DatePickerDialog(HostPreRegister.this, new DatePickerDialog.OnDateSetListener() {
//                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
//                            // TODO Auto-generated method stub
//                    /*      Your code   to get date and time    */
//                            selectedmonth = selectedmonth + 1;
//                            etDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
//                        }
//                    }, mYear, mMonth, mDay);
//                    mDatePicker.setTitle("Select Date");
//                    mDatePicker.show();
//                }
//
//        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
                etName = (EditText) findViewById(R.id.editTextName);
                etEmail = (EditText) findViewById(R.id.editTextEmail);
                etTime = (EditText) findViewById(R.id.editTextTime);
                etDate = (EditText) findViewById(R.id.editTextDate);
                etHostEmail = (EditText) findViewById(R.id.editTextHostEmail);
                HttpRequest request = new HttpRequest("http://ruixian-ang97.000webhostapp.com/insertVisitor.php");
                request.setMethod("POST");
                request.addData("visitor_email", etEmail.getText().toString());
                request.addData("visitor_name", etName.getText().toString());
                request.addData("handphone_number", etNumber.getText().toString());
                request.execute();
                try {
                    HttpRequest request1 = new HttpRequest("http://ruixian-ang97.000webhostapp.com/insertVisitorInfo.php");
                    request1.setMethod("POST");
                    request1.addData("visitor_email", etEmail.getText().toString());
                    request1.addData("date_in", etDate.getText().toString());
                    request1.addData("time_in", etTime.getText().toString());
                    request1.addData("user_email", etHostEmail.getText().toString());
                    request1.execute();
                    Toast.makeText(HostPreRegister.this, "Visitor inserted", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
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
                    etSv2Sub2 = (EditText) viewDialog.findViewById(R.id.editTextSv2Sub1);
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

    protected void onStart(){
        super.onStart();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
