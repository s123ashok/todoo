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

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class ToDooActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST = 10 ;
    private EditText txAdd;
    private ListView lvAdd;
    private ArrayAdapter<Item> itemsAdapter;
    private ArrayList<Item> items = new ArrayList<Item>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_doo);
        txAdd = (EditText) findViewById(R.id.txAdd);
        lvAdd = (ListView) findViewById(R.id.lvTodo);

        itemsAdapter = new MyCustomArrayAdapter(this, items);
        refreshItems();

        lvAdd.setAdapter(itemsAdapter);

        setupListViewListeners();
    }



    private void setupListViewListeners() {
        lvAdd.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new Delete().from(Item.class).where("remote_id = ?", items.get(position).remoteId).execute();

                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ToDooActivity.this, EditItemActivity.class);
                i.putExtra("position", Integer.toString(position));
                i.putExtra("value", items.get(position).name);
                startActivityForResult(i, EDIT_REQUEST);
            }
        });
    }

    private void refreshItems() {
        // Query ActiveAndroid for list of data
        List<Item> queryResults = new Select().from(Item.class)
                .orderBy("Priority ASC").limit(100).execute();

        itemsAdapter.clear();
        // Load the result into the adapter using `addAll`
        if (null == queryResults || queryResults.isEmpty()){
            return;
        }
        else{
            itemsAdapter.addAll(queryResults);
        }
    }

    public void onAddItem(View view) {
        String itemName = txAdd.getText().toString();
        itemsAdapter.add(new Item(itemName));
        txAdd.setText("");
        //writeItems();
    }



    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == EDIT_REQUEST)
        {
            if (data.getExtras().getInt("code")==100){ // Value edited
                int pos = data.getExtras().getInt("pos");
                String priority = data.getExtras().getString("priority");
                Item itemOld = items.get(pos);
                Item itemNew = new Item(itemOld.remoteId, data.getExtras().getString("value"),priority);

                refreshItems();
                itemsAdapter.notifyDataSetChanged();
     //           writeItems();
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


}



