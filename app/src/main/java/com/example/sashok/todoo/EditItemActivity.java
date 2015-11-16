package com.example.sashok.todoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {

    private EditText txEdit ;
    private String initStr = null;
    private int pos;
    private Spinner spinner1;
    private String priority = "Low";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        pos = Integer.parseInt(getIntent().getStringExtra("position"));
        initStr = getIntent().getStringExtra("value");
        txEdit = (EditText) findViewById(R.id.txEdit);
        txEdit.setText(initStr);
        txEdit.setSelection(initStr.length());
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = parent.getItemAtPosition(pos).toString();
            //    Toast.makeText(parent.getContext(), priority, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void onSave(View view) {
        String editStr = txEdit.getText().toString();
         if (editStr.equals("")){
            Toast.makeText(this,"Item empty, Cancel and delete in main screen", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(this,"Saving the Changes", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("code",100); // Code for valid edit/update
            i.putExtra("pos",pos);
            i.putExtra("value",editStr);
            i.putExtra("priority",priority);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void onCancel(View view) {
        Intent i = new Intent();
        i.putExtra("code",200); // Code for cancel
        setResult(RESULT_OK,i);
        finish();
    }
}
