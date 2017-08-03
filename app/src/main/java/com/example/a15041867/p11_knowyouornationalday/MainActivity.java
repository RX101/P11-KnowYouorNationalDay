package com.example.a15041867.p11_knowyouornationalday;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter aa;
    ArrayList<String> al;
    ListView lv;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id.lv);
        al = new ArrayList<String >();
        al.add("Singapore National Day is on 9 Aug");
        al.add("Singapore is 52 years old");
        al.add("Theme is #OneNation Together");
        aa = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,al);
        lv.setAdapter(aa);

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS);
        if (permissionCheck != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},2);

        }


        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.login, null);
        final EditText etPassphrase = (EditText) passPhrase
                .findViewById(R.id.editTextPassPhrase);
        etPassphrase.setText("738964");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Login")
                .setView(passPhrase)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String phrase = etPassphrase.getText().toString();
                        if(phrase.equals("738964")){
                            Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("password", phrase);
                            editor.commit();
                        }else{
                            Toast.makeText(MainActivity.this,"Login unSuccessful",Toast.LENGTH_LONG).show();
                           finish();
                        }
                    }
                });

        builder.setNegativeButton("NO ACCESS CODE",null);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String pw = pref.getString("password", "");
        if (pw.equalsIgnoreCase("738964")) {
            alertDialog.dismiss();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_quit){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("QUIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("No",null);
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else if (item.getItemId() == R.id.action_sendToFriend){
            String [] list = new String[] { "Email", "SMS" };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            for(int i = 1; i< al.size(); i++){
                                 message += i + al.get(i) + "\n";
                            }
                            if (which == 0) {
                                // The action you want this intent to do;
                                // ACTION_SEND is used to indicate sending text
                                Intent email = new Intent(Intent.ACTION_SEND);
                                // Put essentials like email address, subject & body text
                                email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"jason_lim@rp.edu.sg"});
                                email.putExtra(Intent.EXTRA_SUBJECT,
                                        "National Day");
                                email.putExtra(Intent.EXTRA_TEXT,
                                        message);
                                // This MIME type indicates email
                                email.setType("message/rfc822");
                                // createChooser shows user a list of app that can handle
                                // this MIME type, which is, email
                                startActivity(Intent.createChooser(email,
                                        "Choose an Email client :"));

                            } else if (which == 1) {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage("5556", null,message, null, null);
                                Toast.makeText(MainActivity.this,"Sent",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }else if (item.getItemId() == R.id.action_quiz){
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout passPhrase =
                    (LinearLayout) inflater.inflate(R.layout.quiz, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Test Yourself!")
                    .setView(passPhrase)
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    })
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            RadioGroup rg1 = (RadioGroup)passPhrase.findViewById(R. id. rg1);
                            RadioGroup rg2 = (RadioGroup)passPhrase.findViewById(R. id. rg2);
                            RadioGroup rg3 = (RadioGroup)passPhrase.findViewById(R. id. rg3);

                            int first = rg1.getCheckedRadioButtonId();
                            int second = rg2.getCheckedRadioButtonId();
                            int third = rg3.getCheckedRadioButtonId();

                            RadioButton rb1 = (RadioButton)passPhrase.findViewById(first);
                            RadioButton rb2 = (RadioButton)passPhrase.findViewById(second);
                            RadioButton rb3 = (RadioButton)passPhrase.findViewById(third);
                            int score = 0;
                            if (rb1.getText().toString().equalsIgnoreCase("No")) {
                                score += 1;
                            } else {
                            }
                            if (rb2.getText().toString().equalsIgnoreCase("Yes")) {
                                score += 1;
                            } else {
                            }
                            if (rb3.getText().toString().equalsIgnoreCase("Yes")) {
                                score += 1;
                            } else {
                            }
                            Toast.makeText(MainActivity.this, "Score " + score,
                                    Toast.LENGTH_LONG).show();
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);

        }
        return super.onOptionsItemSelected(item);
    }
}
