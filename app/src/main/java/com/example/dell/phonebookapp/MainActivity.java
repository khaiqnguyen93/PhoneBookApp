package com.example.dell.phonebookapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dell.phonebookapp.Adapter.ContactAdapter;
import com.example.dell.phonebookapp.Model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private List<Contact> arrayContact;
    private ContactAdapter adapter;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton rdoMale;
    private RadioButton rdoFemale;
    private Button btnAddContact;
    private ListView LvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact = new ArrayList<>();
        adapter = new ContactAdapter(this, R.layout.contact_item_list, arrayContact);
        LvContact.setAdapter(adapter);
        checkPermissionAndroid();
        LvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showConfirmDialog(position);
            }
        });
    }

    //Bind control items to variables
    public void setWidget() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtNumber = (EditText) findViewById(R.id.edt_number);
        rdoMale = (RadioButton) findViewById(R.id.rdo_male);
        rdoFemale = (RadioButton) findViewById(R.id.rdo_fmale);
        btnAddContact = (Button) findViewById(R.id.btn_add);
        LvContact = (ListView) findViewById(R.id.lv_contact);
    }

    //Add contact
    public void addContact(View view) {
        if (view.getId() == R.id.btn_add) {
            //Validate txtbox
            String name = edtName.getText().toString().trim();
            String number = edtNumber.getText().toString().trim();
            boolean isMale = true;
            if (rdoMale.isChecked()) {
                isMale = true;
            } else {
                isMale = false;
            }
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)) {
                Toast.makeText(this, "Please input name and number", Toast.LENGTH_LONG).show();
            } else {
                Contact contact = new Contact(isMale, name, number);
                arrayContact.add(contact);
                clearAll();
            }
            //
            adapter.notifyDataSetChanged();
        }
    }

    // Remove all info after insert
    public void clearAll() {
        edtName.setText("");
        edtNumber.setText("");
    }

    // Pop up dialog
    public void showConfirmDialog(final int contactNo) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.contact_dialog_layout);
        Button btnCall = (Button) dialog.findViewById(R.id.btn_call);
        Button btnMsg = (Button) dialog.findViewById(R.id.btn_msg);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentCall(contactNo);
                dialog.dismiss();
            }
        });
        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentMessage(contactNo);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // Call method
    private void intentCall(int contactNo) {
        Intent inTnt = new Intent();
        inTnt.setAction(Intent.ACTION_CALL);
        inTnt.setData(Uri.parse("tel:" + arrayContact.get(contactNo).getmNumber()));
        startActivity(inTnt);
    }

    // Message method with 2nd style
    private void intentMessage(int contactNo) {
        Intent inTnt = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + arrayContact.get(contactNo).getmNumber()));
        startActivity(inTnt);
    }

    //Check permisison for call and send msg - pratice
    private void checkPermissionAndroid() {
        String[] permissions = new String[]{
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.SEND_SMS
        };
        List<String> ListPermissionNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ListPermissionNeeded.add(permission);
            }
        }
        if (!ListPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, ListPermissionNeeded.toArray(new String[ListPermissionNeeded.size()]), 1);
        }
    }
}
