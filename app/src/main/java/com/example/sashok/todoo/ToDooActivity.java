package com.example.sashok.todoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDooActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST = 10 ;
    private EditText txAdd;
    private ListView lvAdd;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> items;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_doo);
        File fileDir = getFilesDir();
        file = new File(fileDir,"ToDoo.txt");

        txAdd = (EditText) findViewById(R.id.txAdd);
        lvAdd = (ListView) findViewById(R.id.lvTodo);
        items = new ArrayList<String>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvAdd.setAdapter(itemsAdapter);

        setupListViewListeners();
    }

    private void setupListViewListeners() {
        lvAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ToDooActivity.this, EditItemActivity.class);
                i.putExtra("position", Integer.toString(position));
                i.putExtra("value",items.get(position));
                startActivityForResult(i, EDIT_REQUEST);
            }
        });
    }

    private void writeItems() {
        try {
            FileUtils.writeLines(file, items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void readItems() {
        try{
            items = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST)
        {
            if (data.getExtras().getInt("code")==100){ // Value edited
                int pos = data.getExtras().getInt("pos");
                items.remove(pos);
                items.add(pos,data.getExtras().getString("value"));
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_doo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View view) {
        itemsAdapter.add(txAdd.getText().toString());
        txAdd.setText("");
        writeItems();
    }

}
