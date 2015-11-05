package com.example.sashok.todoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EditItemActivity extends AppCompatActivity {

    private EditText txEdit ;
    private String initStr = null;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        pos = Integer.parseInt(getIntent().getStringExtra("position"));
        initStr = getIntent().getStringExtra("value");
        txEdit = (EditText) findViewById(R.id.txEdit);
        txEdit.setText(initStr);
        txEdit.setSelection(initStr.length());
    }

    public void onSave(View view) {
        String editStr = txEdit.getText().toString();
        if (initStr.equals(editStr))
        {
            Toast.makeText(this,"Item not edited, Cancel to dismiss", Toast.LENGTH_SHORT).show();
            return;
        } else if (editStr.equals("")){
            Toast.makeText(this,"Item empty, Cancel and delete in main screen", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(this,"Saving the Changes", Toast.LENGTH_SHORT).show();
            Intent i = new Intent();
            i.putExtra("code",100); // Code for valid edit/update
            i.putExtra("pos",pos);
            i.putExtra("value",editStr);
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
