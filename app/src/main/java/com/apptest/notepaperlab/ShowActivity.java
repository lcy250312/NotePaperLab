package com.apptest.notepaperlab;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_notedate, txt_notetime, txt_notecontents, txt_color;
    private ImageView lbl_color;
    Button btn_back;
    public Bundle bData;
    public DbAdapter dbAdapter;
//    public int index;
    private String id;
    Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     //   Log.i("DB_ShowActivity","onCreate1");
        initView();
        bData = this.getIntent().getExtras();
        id = bData.getString("item_id");
        Log.i("DB_ShowActivity",id);
        dbAdapter = new DbAdapter(this);
        Cursor cursor = dbAdapter.queryById(id);
//        index = cursor.getInt(0);
        txt_notedate.setText(cursor.getString(1));
        txt_notetime.setText(cursor.getString(2));
        txt_notecontents.setText(cursor.getString(3));
        txt_color.setText(cursor.getString(4));
        lbl_color.setBackgroundColor(Color.parseColor(txt_color.getText().toString()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("type","edit");
                intent.putExtra("item_id",id);
                intent.setClass(ShowActivity.this, EditActivity.class );
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.del_menu,menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                AlertDialog dialog = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(this);
                builder.setTitle("警告")
                        .setMessage(" 請確認是否刪除?")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbAdapter.deleteContacts(id);
                                Intent i = new Intent(ShowActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void initView(){
        txt_notedate = findViewById(R.id.txtNotedate);
        txt_notetime = findViewById(R.id.txtNotetime);
        txt_notecontents = findViewById(R.id.txtNotecontents);
        txt_color = findViewById(R.id.txtColor);
        lbl_color = findViewById(R.id.lbl_Color);
        btn_back = findViewById(R.id.btn_back);
        //按鈕行為設定
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
