package com.example.a15041867.vms;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText etOldPassword, etNewPassword,etConfirmPassword;
    private Button btnChangePassword;
    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private Intent i, intentAPI;
    private String apikey, userEmail, oldPassword,newPassword,confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Change Password");
        setContentView(R.layout.activity_change_password);
        etOldPassword = (EditText)findViewById(R.id.etOldPassword);
        etNewPassword = (EditText)findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        btnChangePassword = (Button)findViewById(R.id.btnChangePassword);

        intentAPI = getIntent();
        apikey = intentAPI.getStringExtra("api");
        userEmail = intentAPI.getStringExtra("user_email");
        Log.i("ChangePasswordActivity ","" + apikey + userEmail);

        //        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvChangePassword);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutChangePassword);
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

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 oldPassword = etOldPassword.getText().toString();
                 newPassword = etNewPassword.getText().toString();
                 confirmPassword = etConfirmPassword.getText().toString();
                if(oldPassword.equals("")){
                    etOldPassword.setError("Please enter your old password.");
                }else if(newPassword.equals("")){
                    etNewPassword.setError("Please enter your new password");
                }else if(confirmPassword.equals("")){
                    etConfirmPassword.setError("Please enter the confirm password.");
                }else if(!confirmPassword.equals(newPassword)){
                    etConfirmPassword.setError("New password and confirm password is not matches.");
                }else {

                    // Check if there is network access
                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        /******************************/
                        if (apikey != null) {


                            //Create the Dialog Builder
                            AlertDialog.Builder myBuilder = new AlertDialog.Builder(ChangePasswordActivity.this);
                            //Set the dialog details
                            myBuilder.setTitle("Change Password:");
                            myBuilder.setMessage("Are you sure to change password ? ");
                            myBuilder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/changePassword.php");
                                    request.setMethod("POST");
                                    request.addData("apikey", apikey);
                                    request.addData("new", newPassword);
                                    request.addData("old", oldPassword);
                                    request.addData("email", userEmail);
                                    request.execute();

                                    try {
                                        String jsonString = request.getResponse();
                                        Log.d("jsonString: ",jsonString);
                                        JSONObject jsonObj = new JSONObject(jsonString);
                                        String message = jsonObj.getString("message");
                                        Toast.makeText(ChangePasswordActivity.this, message, Toast.LENGTH_LONG).show();
                                        finish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            myBuilder.setNeutralButton("Cancel", null);
                            AlertDialog myDialog = myBuilder.create();
                            myDialog = myBuilder.create();
                            myDialog.show();


                        } else {
                            Toast.makeText(ChangePasswordActivity.this, "Login Failed, API Key is wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ChangePasswordActivity.this,"No Network Connection! ",Toast.LENGTH_SHORT).show();
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
        Log.i("ChangePasswordActivity ","" + apikey + userEmail);
    }

    public void logout(){
        Intent intentLogout = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intentLogout);
    }
}
