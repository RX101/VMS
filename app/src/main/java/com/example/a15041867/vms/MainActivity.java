package com.example.a15041867.vms;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.LocaleDisplayNames;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText etLoginEmail, etLoginPassword;
    private TextView tvForgetPassword, tvErrorMessage;
    private Session session;
    Intent i, intentLogin;
    boolean userFound;
    private String shareAPI, shareUserEmail, sharePosition, pst;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_main);

        btnLogin =(Button)findViewById(R.id.buttonLogin);
        etLoginEmail = (EditText)findViewById(R.id.editTextloginEmail);
        etLoginPassword = (EditText)findViewById(R.id.editTextloginPassword);
        tvForgetPassword = (TextView) findViewById(R.id.textViewForgetPassword);
        tvErrorMessage = (TextView) findViewById(R.id.textViewErrorMessage);
        session = new Session(this);
//        if(session.loggedin()) {
//            if(pst.equals("security staff")){
//                intentLogin = new Intent(getApplicationContext(), SignInActivity.class);
//            }else if(pst.equals("manager")){
//                intentLogin = new Intent(getApplicationContext(), UserInfoActivity.class);
//            }else if(pst.equals("host")) {
//                intentLogin = new Intent(getApplicationContext(), HostHomePage.class);
//            }else if(pst.equals("position")){
//                Toast.makeText(getBaseContext(),"Position",Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_LONG).show();
//            }
//
//            intentLogin.putExtra("api", shareAPI);
//            intentLogin.putExtra("user_email", shareUserEmail);
//            startActivity(intentLogin);
//            finish();
//        }

        etLoginEmail.setText("manager@gmail.com");
        etLoginPassword.setText("manager1234");

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS);
        if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},2);

        }


        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentLogin = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                startActivity(intentLogin);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
//        shareAPI=prefs.getString("apikey","null");
//        shareUserEmail = prefs.getString("useremail","null");
//        pst = prefs.getString("sharePosition","position");
        Log.d("Position",pst + "\n" + shareAPI);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userFound = false;
                // Check if there is network access
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                String email = etLoginEmail.getText().toString();
                String password = etLoginPassword.getText().toString();

                if (networkInfo != null && networkInfo.isConnected()) {

                    if(email.equals("")){
                        etLoginEmail.setError("Email field is required");
                    }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        etLoginEmail.setError("Invalid Email, Please try again.");
                    }else if(password.equals("")){
                        etLoginPassword.setError("Password field is required");
                    }else {

                        HttpRequest requestCheckUser = new HttpRequest("https://ruixian-ang97.000webhostapp.com/checkUser.php");
                        requestCheckUser.setMethod("POST");
                        requestCheckUser.execute();
                        try{
                            String jsonString = requestCheckUser.getResponse();
                            Log.d(" requestCheckUser ", jsonString);
                            JSONArray jsonArray = new JSONArray(jsonString);
                            for(int i=0; i< jsonArray.length();i++) {
                                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                                String userEmail = jsonObj.getString("user_email");
                                if(email.equalsIgnoreCase(userEmail)){
                                    userFound = true;
                                    Log.d("loop","LOOP" +  i + "\n" + email + "\n" + userEmail);
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if(userFound == true){
                            HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/doLogin.php");
                            request.setMethod("POST");
                            request.addData("email", etLoginEmail.getText().toString());
                            request.addData("password", etLoginPassword.getText().toString());
                            request.execute();
                            /******************************/

                            try {

                                String jsonString = request.getResponse();
                                Log.d("info", jsonString);

                                JSONObject jsonObj = (JSONObject) new JSONTokener(jsonString).nextValue();
                                if (jsonObj.getBoolean("authenticated")) {

                                    // When authentication is successful
                                    //TODO 02 Extract the API Key from the JSON object and assign it to the following variable
                                    String apiKey = jsonObj.getString("apikey");
                                    String useremail = jsonObj.getString("user_email");
                                    String position = jsonObj.getString("position");
                                    String block = jsonObj.getString("block");
                                    String unit = jsonObj.getString("unit");
                                    if (position.equals("security staff")) {
                                        intentLogin = new Intent(getApplicationContext(), SignInActivity.class);
//                                        session.setLoggedin(true);
//                                        sharePosition = "security staff";
                                    } else if (position.equals("manager")) {
                                        intentLogin = new Intent(getApplicationContext(), UserInfoActivity.class);
//                                        session.setLoggedin(true);
//                                        sharePosition = "manager";
                                    } else if (position.equals("host")) {
                                        intentLogin = new Intent(getApplicationContext(), HostHomePage.class);
//                                        session.setLoggedin(true);
//                                        sharePosition = "host";
                                    }
                                    intentLogin.putExtra("api", apiKey);
                                    intentLogin.putExtra("user_email", useremail);
                                    startActivity(intentLogin);
//                                    //Open Shaared Preference
//                                    SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                                    //create a edit Shared Preference
//                                    SharedPreferences.Editor prefEdit = prefs.edit();
//                                    //set a key-value pair in preferences editor
//                                    prefEdit.putString("apikey",apiKey);
//                                    prefEdit.putString("useremail",useremail);
//                                    prefEdit.putString("sharePosition",sharePosition);
//                                    prefEdit.commit();

                                } else {
                                    tvErrorMessage.setText("Incorrect Password, please try again");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            etLoginEmail.setError("Incorrect Email, please try again.");
                        }
                    }


                } else {
                    tvErrorMessage.setText("No network connection available.");
                }
            }
        });
    }

}
