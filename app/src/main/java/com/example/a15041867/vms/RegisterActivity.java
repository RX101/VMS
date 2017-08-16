package com.example.a15041867.vms;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegisterName, etRegisterHP, etRegisterEmail;
    Button btnRegister;
    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private String apikey, visitor_name,visitor_email,handphone_number, msg, userEmail;
    private Intent i, intentAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegisterEmail = (EditText)findViewById(R.id.editTextRegisterEmail);
        etRegisterHP = (EditText)findViewById(R.id.editTextRegisterHP);
        etRegisterName = (EditText)findViewById(R.id.editTextRegisterName);
        btnRegister = (Button)findViewById(R.id.buttonRegister);
        session = new Session(this);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");
        userEmail = intentAPI.getStringExtra("user_email");
        Log.i("Register Activity :","" + apikey + userEmail);

//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvRegister);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutRegister);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

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


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etRegisterEmail.getText().toString().trim();
                final String hp = etRegisterHP.getText().toString().trim();
                final String name = etRegisterName.getText().toString().trim();

                if(name.equals("")){
                    etRegisterName.setError("Name is empty");
                }else if (hp.equals("")){
                    etRegisterHP.setError("Handphone Number field is empty");
                }else if(email.equals("")){
                    etRegisterEmail.setError("Email field is empty");
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etRegisterEmail.setError("Invalid Email, Please try again.");
                }else{
                    HttpRequest requestVisitorDetails= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php");
//                    HttpRequest request= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getVisitor.php?apikey=d2bfa8e8b749d2772a21edee7b70a2b3");
                    requestVisitorDetails.setMethod("POST");
                    requestVisitorDetails.addData("apikey",apikey);
                    requestVisitorDetails.execute();
                    try{
                        String jsonString = requestVisitorDetails.getResponse();
//                        Log.i("response", jsonString);
                        JSONArray jsonArray = new JSONArray(jsonString);
                        boolean recordFound = false;
                        for(int i=0; i< jsonArray.length();i++){
                            JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                            visitor_email = jsonObj.getString("visitor_email");
                            visitor_name = jsonObj.getString("visitor_name");
                            handphone_number = jsonObj.getString("handphone_number");

                            if(name.equalsIgnoreCase(visitor_name)){
                                recordFound=true;
                                etRegisterName.setError("Visitor name already exist,Please try another one");
//                                Toast.makeText(RegisterActivity.this,"Record: "+ i,Toast.LENGTH_SHORT).show();

                            } else if (hp.equalsIgnoreCase(handphone_number)){
                                recordFound=true;
                                etRegisterHP.setError("Handphone Number has been registered in database,Please try another one");
//                                Toast.makeText(RegisterActivity.this,"Record: "+ i,Toast.LENGTH_SHORT).show();
                            } else if(email.equals(visitor_email)){
                                recordFound=true;
                                etRegisterEmail.setError("Email already exist,Please try another one");
//                                Toast.makeText(RegisterActivity.this,"Record: "+ i,Toast.LENGTH_SHORT).show();
                            }
                        }

                        if(recordFound == false){
                            //insert
                            //Create the Dialog Builder
                                AlertDialog.Builder myBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                //Set the dialog details
                                myBuilder.setTitle("Confirmation visitor information:");
                                myBuilder.setMessage("Visitor Details: \nName: " + name + "\nHandphone Number: "+ hp +"\nEmail: " + email);
                                myBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        HttpRequest requestRegisterVisitor = new HttpRequest(" https://ruixian-ang97.000webhostapp.com/doRegister.php");
                                        requestRegisterVisitor.setMethod("POST");
                                        requestRegisterVisitor.addData("apikey",apikey);
                                        requestRegisterVisitor.addData("visitor_email", email);
                                        requestRegisterVisitor.addData("visitor_name", name);
                                        requestRegisterVisitor.addData("handphone_number", hp);
                                        requestRegisterVisitor.execute();

                                                try{
                                                    String jsonString1 = requestRegisterVisitor.getResponse();
//                                                  Log.i("response", jsonString);
                                                    JSONObject jsonObject1 = new JSONObject(jsonString1);
                                                    String msg = jsonObject1.getString("message");
                                                    etRegisterName.setText("");
                                                    etRegisterHP.setText("");
                                                    etRegisterEmail.setText("");
                                                    Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();
                                                  finish();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                    }
                                });
                                myBuilder.setNeutralButton("Cancel", null);
                                //Create and display the Dialog
                                AlertDialog myDialog = myBuilder.create();
                                myDialog.show();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }


//

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
        Log.i("Register Activity :","" + apikey + userEmail);
    }

    public void logout(){
        Intent intentLogout = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intentLogout);
    }
}
