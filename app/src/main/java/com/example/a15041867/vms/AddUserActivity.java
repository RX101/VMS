package com.example.a15041867.vms;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Random;

public class AddUserActivity extends AppCompatActivity {

    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView nv;
    Intent i;

    private static final String TAG = "AddUserActivity";

    EditText etName, etEmail, etHandphone, etBlock, etUnit;
    Button btnAddUser;
    RadioGroup rgRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        session = new Session(this);
//        if(!session.loggedin()){
//            logout();
//        }

        nv = (NavigationView)findViewById(R.id.nvManager);
//        mToolbar = (Toolbar)findViewById(R.id.nav_action);
//        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayoutUserInfo);
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
                        startActivity(i);
                        break;
                    case(R.id.nav_summary):
                        i= new Intent(getApplicationContext(),SummaryActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_evacuation):
                        i= new Intent(getApplicationContext(),EvacuationActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.nav_add_user):
                        i= new Intent(getApplicationContext(),AddUserActivity.class);
                        startActivity(i);
                        break;
                    case(R.id.log_out):
                        i= new Intent(getApplicationContext(),MainActivity.class);
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

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String handphone = etHandphone.getText().toString();
                String block = etBlock.getText().toString();
                String unit = etUnit.getText().toString();

                // Get the Id of the selected radio button in the RadioGroup
                int selectedButtonId = rgRoles.getCheckedRadioButtonId();
                // Get the radio button object from the Id we had gotten above
                RadioButton rb = (RadioButton) findViewById(selectedButtonId);
                String selectedPosition = rb.getText().toString();

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
                String password = sb.toString();

                HttpRequest request = new HttpRequest("http://10.0.2.2/C302_p08_SecureCloudAddressBook/createNewEntry.php");
                request.setMethod("POST");
                request.addData("user_email", email);
                request.addData("name", name);
                request.addData("handphone_number", handphone);
                request.addData("position", selectedPosition);
                request.addData("block", block);
                request.addData("unit",unit);
                request.addData("password", password);
                request.execute();
                Toast.makeText(AddUserActivity.this, "Contact Added Successfully", Toast.LENGTH_SHORT).show();

                /******************************/


                try{
                    String jsonString = request.getResponse();
                    Log.d(TAG, "jsonString: " + jsonString);

                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });

    }
}
