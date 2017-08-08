package com.example.a15041867.vms;

import android.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class SignInActivity extends AppCompatActivity {

    EditText etSignInEmail, etSignInVisitUnit, etSignInVisitBlock, etSv2Sub1,etSv2Sub2,
            etSv3Sub1,etSv3Sub2, etSv3Sub3, etSv4Sub1, etSv4Sub2, etSv4Sub3, etSv4Sub4,
            etSv5Sub1, etSv5Sub2, etSv5Sub3, etSv5Sub4, etSv5Sub5;
    TextView tvRegister, tvSubVisitor;
    Spinner spnNumVisitor;
    Button btnSignIn;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private  Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private String apikey, visitor_email,visit_block,visit_unit,sub_visitor,date,time, db_visitor_email,db_block,db_unit, userEmail;
    private Intent i, intentAPI, intentRegister;
    private boolean visitorEmailFound, unitFound, blockFound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etSignInEmail = (EditText)findViewById(R.id.editTextSignInEmail);
        etSignInVisitUnit = (EditText)findViewById(R.id.editTextSignInVisitUnit);
        etSignInVisitBlock = (EditText)findViewById(R.id.editTextSignInVisitBlock);
        tvRegister = (TextView)findViewById(R.id.textViewRegister);
        btnSignIn = (Button) findViewById(R.id.buttonSignIn);
        spnNumVisitor = (Spinner)findViewById(R.id.spinnerNumVisitor);
        tvSubVisitor = (TextView)findViewById(R.id.textViewSubVisitor);


        session = new Session(this);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");
        userEmail = intentAPI.getStringExtra("user_email");
        Log.i("Sign In Activity","" + apikey);
        //        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvSignIn);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutSignIn);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.close, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_sign_in):
                        i = new Intent(getApplicationContext(),SignInActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_sign_out):
                        i= new Intent(getApplicationContext(),SignOutActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_register):
                        i= new Intent(getApplicationContext(),RegisterActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_change_password):
                        i= new Intent(getApplicationContext(),ChangePasswordActivity.class);
                        i.putExtra("api",apikey);
                        i.putExtra("user_email",userEmail);
                        startActivity(i);
                        break;
                    case(R.id.nav_log_out):
                        logout();
                        break;
                }
                return true;
            }
        });

        etSignInEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                return false;
            }
        });
        spnNumVisitor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spnNumVisitor.getSelectedItemPosition() == 1){
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor2,null);

                    //Obtain th UI component in the input.xml layout
                    etSv2Sub1 = (EditText)viewDialog.findViewById(R.id.editTextSv2Sub1);
                    etSv2Sub2 = (EditText)viewDialog.findViewById(R.id.editTextSv2Sub2);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignInActivity.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv2Sub1 =  etSv2Sub1.getText().toString();
                            String sv2Sub2 =  etSv2Sub2.getText().toString();

                            //Update the Text entered by the user
                            if(etSv2Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 1",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv2Sub2.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 2",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else{
                                tvSubVisitor.setText(sv2Sub1 + "," +  sv2Sub2);
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

                }else if (spnNumVisitor.getSelectedItemPosition() == 2){
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor3,null);

                    //Obtain th UI component in the input.xml layout
                    etSv3Sub1 = (EditText)viewDialog.findViewById(R.id.editTextSv3Sub1);
                    etSv3Sub2 = (EditText)viewDialog.findViewById(R.id.editTextSv3Sub2);
                    etSv3Sub3 = (EditText)viewDialog.findViewById(R.id.editTextSv3Sub3);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignInActivity.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv3Sub1 =  etSv3Sub1.getText().toString();
                            String sv3Sub2 =  etSv3Sub2.getText().toString();
                            String sv3Sub3 =  etSv3Sub3.getText().toString();

                            //Update the Text entered by the user
                            if(etSv3Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 1",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv3Sub2.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 2",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv3Sub3.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 3",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else{
                                tvSubVisitor.setText(sv3Sub1 + "," +  sv3Sub2 + "," + sv3Sub3);
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

                }else if (spnNumVisitor.getSelectedItemPosition() == 3){
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor4,null);

                    //Obtain th UI component in the input.xml layout
                    etSv4Sub1 = (EditText)viewDialog.findViewById(R.id.editTextSv4Sub1);
                    etSv4Sub2 = (EditText)viewDialog.findViewById(R.id.editTextSv4Sub2);
                    etSv4Sub3 = (EditText)viewDialog.findViewById(R.id.editTextSv4Sub3);
                    etSv4Sub4 = (EditText)viewDialog.findViewById(R.id.editTextSv4Sub4);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignInActivity.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv4Sub1 =  etSv4Sub1.getText().toString();
                            String sv4Sub2 =  etSv4Sub2.getText().toString();
                            String sv4Sub3 =  etSv4Sub3.getText().toString();
                            String sv4Sub4 =  etSv4Sub4.getText().toString();

                            //Update the Text entered by the user
                            if(etSv4Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 1",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv4Sub2.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 2",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv4Sub3.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 3",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv4Sub4.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 4",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            } else{
                                tvSubVisitor.setText(sv4Sub1 + "," +  sv4Sub2 + "," + sv4Sub3 + "," + sv4Sub4);
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

                }else if (spnNumVisitor.getSelectedItemPosition() == 4){
                    //Inflate the input.xml layout file
                    LayoutInflater inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialog = inflater.inflate(R.layout.sub_visitor5,null);

                    //Obtain th UI component in the input.xml layout
                    etSv5Sub1 = (EditText)viewDialog.findViewById(R.id.editTextSv5Sub1);
                    etSv5Sub2 = (EditText)viewDialog.findViewById(R.id.editTextSv5Sub2);
                    etSv5Sub3 = (EditText)viewDialog.findViewById(R.id.editTextSv5Sub3);
                    etSv5Sub4 = (EditText)viewDialog.findViewById(R.id.editTextSv5Sub4);
                    etSv5Sub5 = (EditText)viewDialog.findViewById(R.id.editTextSv5Sub5);

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignInActivity.this
                    );

                    //Set the view of the dialog
                    myBuilder.setView(viewDialog);
                    myBuilder.setTitle("Add Sub Visitor");
                    myBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Extract the Text entered by the user
                            String sv5Sub1 =  etSv5Sub1.getText().toString();
                            String sv5Sub2 =  etSv5Sub2.getText().toString();
                            String sv5Sub3 =  etSv5Sub3.getText().toString();
                            String sv5Sub4 =  etSv5Sub4.getText().toString();
                            String sv5Sub5 =  etSv5Sub5.getText().toString();

                            //Update the Text entered by the user
                            if(etSv5Sub1.getText().toString().equals("")) {
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 1",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv5Sub2.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 2",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv5Sub3.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 3",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv5Sub4.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 4",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }else if(etSv5Sub5.getText().toString().equals("")){
                                Toast.makeText(getBaseContext(),"Please enter sub visitor 5",Toast.LENGTH_SHORT).show();
                                spnNumVisitor.setSelection(0);
                            }  else{
                                tvSubVisitor.setText(sv5Sub1 + "," +  sv5Sub2 + "," + sv5Sub3 + "," + sv5Sub4 + "," + sv5Sub5);
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
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentRegister = new Intent(SignInActivity.this,RegisterActivity.class);
                intentRegister.putExtra("api",apikey);
                startActivity(intentRegister);
            }
        });

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visitor_email = etSignInEmail.getText().toString();
                visit_block = etSignInVisitBlock.getText().toString();
                visit_unit = etSignInVisitUnit.getText().toString();
                sub_visitor = tvSubVisitor.getText().toString();

                visitorEmailFound = false;
                unitFound = false;
                blockFound = false;

                Calendar now = Calendar.getInstance(); //Create a Calendar object with current date/time
                date = now.get(Calendar.YEAR)+ "/"+
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.DAY_OF_MONTH);
                time  = now.get(Calendar.HOUR_OF_DAY)+":"+
                        now.get(Calendar.MINUTE)+":"+now.get(Calendar.SECOND);

                if(visitor_email.equals("")){
                    etSignInEmail.setError("Email field is empty");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(visitor_email).matches()){
                    etSignInEmail.setError("Invalid Email, Please try again.");
                }else if (visit_block.equals("")){
                    etSignInVisitBlock.setError("Block field is empty");
                }else if(visit_unit.equals("")){
                    etSignInVisitUnit.setError("Unit field is empty");
                }else{
                    HttpRequest requestVisitorEmail= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php");
                    requestVisitorEmail.setMethod("POST");
                    requestVisitorEmail.addData("apikey",apikey);
                    requestVisitorEmail.execute();
                    try {
                        String jsonString = requestVisitorEmail.getResponse();
//                        Log.i("response", jsonString);
                        JSONArray jsonArray = new JSONArray(jsonString);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                            db_visitor_email = jsonObj.getString("visitor_email");

                            if(visitor_email.equalsIgnoreCase(db_visitor_email)){
                                visitorEmailFound = true;
                            }
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    HttpRequest requestUserDetails= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getUser.php");
                    requestUserDetails.setMethod("POST");
                    requestUserDetails.addData("apikey",apikey);
                    requestUserDetails.execute();
                    try{
                        String jsonString2 = requestUserDetails.getResponse();
//                        Log.i("response", jsonString2);
                        JSONArray jsonArray = new JSONArray(jsonString2);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                            db_block = jsonObj.getString("block");
                            db_unit = jsonObj.getString("unit");
                            if(visit_block.equalsIgnoreCase(db_block)){
                                blockFound = true;
                            }
                            if(visit_unit.equalsIgnoreCase(db_unit)){
                                unitFound = true;
                            }
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if(visitorEmailFound == true && unitFound == true && blockFound == true){
                        //Create the Dialog Builder
                        AlertDialog.Builder myBuilder = new AlertDialog.Builder(SignInActivity.this);
                        //Set the dialog details
                        myBuilder.setTitle("Confirmation Sign In information:");
                        myBuilder.setMessage("Visitor Details: \nEmail: " + visitor_email + "\nVisit Unit: "+ visit_block + " " + visit_unit +"\nSub-visitor: " + sub_visitor);
                        myBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HttpRequest requestSignIn= new HttpRequest("https://ruixian-ang97.000webhostapp.com/doSignIn.php");
                                requestSignIn.setMethod("POST");
                                requestSignIn.addData("apikey",apikey);
                                requestSignIn.addData("user_block",visit_block);
                                requestSignIn.addData("visitor_email",visitor_email);
                                requestSignIn.addData("sub_visitor",sub_visitor);
                                requestSignIn.addData("date_in",date);
                                requestSignIn.addData("time_in",time);
                                requestSignIn.addData("unit",visit_unit);
                                requestSignIn.addData("isSignIn","1");
                                requestSignIn.execute();
                                try{
                                    String jsonString1 = requestSignIn.getResponse();
                  Log.i("response", jsonString1);
                                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                                    String msg = jsonObject1.getString("message");
                                    Toast.makeText(SignInActivity.this,msg,Toast.LENGTH_LONG).show();
                                    etSignInEmail.setText("");
                                    etSignInVisitUnit.setText("");
                                    tvSubVisitor.setText("");
                                    etSignInVisitBlock.setText("");
                                    spnNumVisitor.setSelection(0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        });
                        myBuilder.setNeutralButton("Cancel",null);
                    AlertDialog myDialog = myBuilder.create();
                    myDialog = myBuilder.create();
                    myDialog.show();
                    }else if(visitorEmailFound == false){
                        etSignInEmail.setError("Email address have not register yet,Please register.");
                    }else if(blockFound == false){
                        etSignInVisitBlock.setError("Block does not exist.");
                    }else if(unitFound == false){
                        etSignInVisitUnit.setError("Unit does not exist.");
                    }else{
                        Toast.makeText(SignInActivity.this,"False",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
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
        apikey = intentAPI.getStringExtra("api");
        userEmail = intentAPI.getStringExtra("user_email");
        Log.i("Sign In Activity","" + apikey + userEmail);
    }

    public void logout(){
        Intent intentLogout = new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intentLogout);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                String visitInfo_id = barcode.displayValue;
                HttpRequest requestSignInQR= new HttpRequest("https://ruixian-ang97.000webhostapp.com/doSignInWithQR.php");
                requestSignInQR.setMethod("POST");
                requestSignInQR.addData("apikey",apikey);
                requestSignInQR.addData("id",visitInfo_id);
                requestSignInQR.execute();
                try{
                    String jsonString1 = requestSignInQR.getResponse();
                    Log.i("response", jsonString1);
                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                    String msg = jsonObject1.getString("message");
                    Toast.makeText(SignInActivity.this,msg,Toast.LENGTH_LONG).show();
                    etSignInEmail.setText("");
                    etSignInVisitUnit.setText("");
                    tvSubVisitor.setText("");
                    etSignInVisitBlock.setText("");
                    spnNumVisitor.setSelection(0);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


}
