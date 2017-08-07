package com.example.a15041867.vms;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class AddUserActivity extends AppCompatActivity {

    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    private String apikey, db_email, handphone, password;
    Intent i;
    private boolean EmailFound=false;

    private static final String TAG = "AddUserActivity";

    EditText etName, etEmail, etHandphone, etBlock, etUnit;
    Button btnAddUser;
    RadioGroup rgRoles;
    TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        i = getIntent();
        apikey = i.getStringExtra("api");

        session = new Session(this);
//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvAddUser);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutAddUser);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case(R.id.nav_visitor_info):
                        i = new Intent(getApplicationContext(),VisitorInfoActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_user_info):
                        i= new Intent(getApplicationContext(),UserInfoActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_summary):
                        i= new Intent(getApplicationContext(),SummaryActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case(R.id.nav_evacuation):
                        i= new Intent(getApplicationContext(),EvacuationActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                    case(R.id.log_out):
                        i= new Intent(getApplicationContext(),MainActivity.class);
                        i.putExtra("api", apikey);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });

        btnAddUser = (Button)findViewById(R.id.btnAdd);
        etName = (EditText)findViewById(R.id.etUserName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etHandphone = (EditText)findViewById(R.id.etPhoneNumber);
        etBlock = (EditText)findViewById(R.id.etBlock);
        etUnit = (EditText)findViewById(R.id.etUnit);
        rgRoles = (RadioGroup)findViewById(R.id.rgRole);
        tvError = (TextView)findViewById(R.id.tvError);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                handphone = etHandphone.getText().toString();
                String block = etBlock.getText().toString();
                String unit = etUnit.getText().toString();


                // Get the Id of the selected radio button in the RadioGroup
                int selectedButtonId = rgRoles.getCheckedRadioButtonId();
                // Get the radio button object from the Id we had gotten above
                String selectedPosition = "";
                if(selectedButtonId != -1){
                    RadioButton rb = (RadioButton) findViewById(selectedButtonId);
                    selectedPosition= rb.getText().toString();
                }


                Random rand = new Random();
                //Java random passcode generator alphanumeric
                int passwordSize = 10;
                char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
                StringBuilder sb = new StringBuilder();
                Random random = new Random();
                for (int i = 0; i < passwordSize; i++) {
                    char c = chars[random.nextInt(chars.length)];
                    sb.append(c);
                }
                password = sb.toString();
                String userapikey = name + password;

                if (name.equals("")) {
                    etName.setError("Name field is empty");
                }else if(email.equals("")){
                        etEmail.setError("Please key in your email");
                }else if (handphone.equals("")){
                    etHandphone.setError("Handphone Number field is empty");
                }else if(block.equals("")) {
                    etBlock.setError("Block is empty");
                } else if(unit.equals("")) {
                    etUnit.setError("Unit is empty");
                } else if(selectedPosition.equals("")){
                    tvError.setText("Please select your role");
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError("Invalid Email, Please try again.");
                }else {
                    HttpRequest requestUserDetails= new HttpRequest("https://ruixian-ang97.000webhostapp.com/getUser.php");
                    requestUserDetails.setMethod("POST");
                    requestUserDetails.addData("apikey",apikey);
                    requestUserDetails.execute();
                    EmailFound = false;
                    try{
                        String jsonString2 = requestUserDetails.getResponse();
                        Log.i("response", jsonString2);
                        JSONArray jsonArray = new JSONArray(jsonString2);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                            db_email = jsonObj.getString("user_email");
                            if(email.equalsIgnoreCase(db_email)){
                                EmailFound = true;
                            }
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if(EmailFound==false){
                        HttpRequest request = new HttpRequest("https://ruixian-ang97.000webhostapp.com/addUser.php");
                        request.setMethod("POST");
                        request.addData("user_email", email);
                        request.addData("name", name);
                        request.addData("handphone_number", handphone);
                        request.addData("position", selectedPosition);
                        request.addData("block", block);
                        request.addData("unit",unit);
                        request.addData("password", password);
                        request.addData("apikey", userapikey);
                        request.execute();
                        sendSMSMessage();
                        showAlert("User Added Successfully");
                        //Toast.makeText(AddUserActivity.this, "Contact Added Successfully", Toast.LENGTH_SHORT).show();

                        /******************************/
                        try{
                            String jsonString = request.getResponse();
                            Log.d(TAG, "jsonString: " + jsonString);

                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                    else{
                        etEmail.setError("Please use a different email");
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private void showAlert(String msg){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        AddUserActivity.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = builder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    protected void sendSMSMessage() {
        try{
            String message = "Your password is " + password;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(handphone, null, message, null, null);
            Toast.makeText(AddUserActivity.this, "SMS sent.", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(AddUserActivity.this,ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}
