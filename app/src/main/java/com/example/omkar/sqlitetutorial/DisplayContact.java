package com.example.omkar.sqlitetutorial;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.support.annotation.RequiresPermission;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends ActionBarActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;

    EditText name ;
    EditText phone;
    EditText email;
    EditText area;
    EditText city;
    Spinner state;
    EditText address;
    TextView zipcode;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (EditText) findViewById(R.id.txtName);
        phone = (EditText) findViewById(R.id.txtNumber);
        email = (EditText) findViewById(R.id.txtEmail);
        area = (EditText) findViewById(R.id.txtArea);
        city = (EditText) findViewById(R.id.txtCity);
        state = (Spinner) findViewById(R.id.ddlState);
        address = (EditText) findViewById(R.id.txtAddress);
        zipcode = (TextView) findViewById(R.id.lblZipcode);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String are = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_AREA));
                String cit = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));
                String addres = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_Address));
                String stat = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STATE));
                String zipcod = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_ZIPCODE));

                if (!rs.isClosed())
                {
                    rs.close();
                }
                RelativeLayout actionButtons = (RelativeLayout)findViewById(R.id.actionButtons);
                actionButtons.setVisibility(View.GONE);
                LinearLayout accessButtons = (LinearLayout) findViewById(R.id.accessButtons);
                accessButtons.setVisibility(View.VISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence)emai);
                email.setFocusable(false);
                email.setClickable(false);

                area.setText((CharSequence)are);
                area.setFocusable(false);
                area.setClickable(false);

                city.setText((CharSequence)cit);
                city.setFocusable(false);
                city.setClickable(false);

                address.setText((CharSequence)addres);
                address.setFocusable(false);
                address.setClickable(false);

                zipcode.setText((CharSequence)zipcod);
                zipcode.setFocusable(false);
                zipcode.setClickable(false);

                state.setSelection(((ArrayAdapter)state.getAdapter()).getPosition((CharSequence)stat));
                zipcode.setFocusable(false);
                zipcode.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            }

            else{
                LinearLayout accessButtons = (LinearLayout) findViewById(R.id.accessButtons);
                accessButtons.setVisibility(View.GONE);
                RelativeLayout actionButtons = (RelativeLayout)findViewById(R.id.actionButtons);
                actionButtons.setVisibility(View.VISIBLE);
                //getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.Edit_Contact:
                LinearLayout accessButtons = (LinearLayout)findViewById(R.id.accessButtons);
                accessButtons.setVisibility(View.GONE);
                RelativeLayout actionButtons = (RelativeLayout)findViewById(R.id.actionButtons);
                actionButtons.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                area.setEnabled(true);
                area.setFocusableInTouchMode(true);
                area.setClickable(true);

                city.setEnabled(true);
                city.setFocusableInTouchMode(true);
                city.setClickable(true);

                address.setEnabled(true);
                address.setFocusableInTouchMode(true);
                address.setClickable(true);

                zipcode.setEnabled(true);
                zipcode.setFocusableInTouchMode(true);
                zipcode.setClickable(true);

                state.setEnabled(true);
                zipcode.setFocusableInTouchMode(true);
                zipcode.setClickable(true);

                return true;
            case R.id.Delete_Contact:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteContact(id_To_Update);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void run(View view)
    {
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,name.getText().toString(), phone.getText().toString(), email.getText().toString(), address.getText().toString(), city.getText().toString(), state.getSelectedItem().toString(), area.getText().toString(), zipcode.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                if(mydb.insertContact(name.getText().toString(), phone.getText().toString(), email.getText().toString(), address.getText().toString(), city.getText().toString(), state.getSelectedItem().toString(), area.getText().toString(), zipcode.getText().toString())){
                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }

    public void back(View view){
        super.onBackPressed();
    }

    public void call(View view){
        try {

            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + phone.getText().toString()));
            startActivity(phoneIntent);
        }catch (SecurityException ex){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void message(View view){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setData(Uri.parse("smsto:"));
        sendIntent.putExtra("address"  , phone.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Select an SMS/MMS Client:"));
    }

    public void mail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
    }
}