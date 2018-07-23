package com.apptest.notepaperlab;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
//import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    Spinner spinner;
//    ArrayList<ColorItem> colorItems;
//    SpinnerAdapter spinnerAdapter;
    private ListView listData;
    private LinearLayout lnlItemView;
    private DbAdapter dbAdapter;
    private Intent intent;
    private ImageButton edit;
    public ItemAdapter itemAdapter = null;
    public ArrayList<VwItem> vwitemlist = null;
    public VwItem vwitem = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        colorSpinner();
        displaylistView();
    }

//    private void colorSpinner(){
//        spinner = findViewById(R.id.spinner);
//
//        colorItems = new ArrayList<ColorItem>();
//        colorItems.add(new ColorItem("Red","#ff0000"));
//        colorItems.add(new ColorItem("Green","#00c7a4"));
//        colorItems.add(new ColorItem("Blue","#4b7bd8"));
//        colorItems.add(new ColorItem("Purple","#cf97ca"));
//        spinnerAdapter = new SpinnerAdapter(this,colorItems);
//        spinner.setAdapter(spinnerAdapter);
//    }

    private void displaylistView(){
        listData = findViewById(R.id.listData);
        listData.setEmptyView(findViewById(R.id.noContact));
        dbAdapter = new DbAdapter(this);
        Cursor cursor = dbAdapter.listContacts();

        if (cursor != null) // If Cursordepot is null then do
        // nothing
        {
            if (cursor.moveToFirst()) {
                vwitemlist = new ArrayList<VwItem>();
                do {vwitemlist.add(new VwItem(
                            cursor.getString(cursor.getColumnIndex("_id")),
                            cursor.getString(cursor.getColumnIndex("notedate")),
                            cursor.getString(cursor.getColumnIndex("notetime")),
                            cursor.getString(cursor.getColumnIndex("notecontents")),
                            cursor.getString(cursor.getColumnIndex("color")),
                            cursor.getString(cursor.getColumnIndex("color"))
//                        "#FF4444",
//                        "#FF4444"
                    ));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        itemAdapter = new ItemAdapter(this, vwitemlist);
        listData.setAdapter(itemAdapter);
        //取得點下去當下的資料列
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VwItem vwItem= vwitemlist.get(position);
                Log.i("DBvwItem.notecontents",vwItem.notecontents);
                intent = new Intent();
                intent.putExtra("item_id",vwItem.idt);
                intent.setClass(MainActivity.this, ShowActivity.class );
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                intent = new Intent();
                intent.putExtra("type","add");
                intent.setClass(MainActivity.this, EditActivity.class );
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
