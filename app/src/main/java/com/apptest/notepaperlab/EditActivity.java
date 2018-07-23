package com.apptest.notepaperlab;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    ArrayList<ColorItem> colorItems;
    SpinnerAdapter spinnerAdapter;
    private Intent intent;
    public DbAdapter dbAdapter;
    EditText edt_notedate, edt_notetime, edt_notecontents, edt_clolor;
    Button btn_ok,btn_back,btn_DatePicker,btn_TimePicker;
    TextView txtTitle;
    public String new_notedate,new_notetime,  new_notecontents, new_clolor;
    public Bundle bData;
    public int index;
    private int mYear, mMonth, mDay, mHour, mMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        bData = this.getIntent().getExtras();
        initView();
        dbAdapter = new DbAdapter(this);

        if(bData.getString("type").equals("edit")){
            txtTitle.setText("編輯聯絡人");
            Cursor cursor = dbAdapter.queryById(bData.getString("item_id"));
            Log.i("DB_notetime",cursor.getString(1)+" "+cursor.getString(2));
            index = cursor.getInt(0);
            edt_notedate.setText(cursor.getString(1));
            edt_notetime.setText(cursor.getString(2));
            edt_notecontents.setText(cursor.getString(3));
            edt_clolor.setText(cursor.getString(4));
            spinner.setSelection(getPostiton(edt_clolor.getText().toString(),colorItems));
        }
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
                                dbAdapter.deleteContacts(String.valueOf(index));
                                Intent i = new Intent(EditActivity.this, MainActivity.class);
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
        txtTitle = findViewById(R.id.txtTitle);
        edt_notedate = findViewById(R.id.edtNoteDate);
        edt_notetime = findViewById(R.id.edtNoteTime);
        edt_notecontents = findViewById(R.id.edtNotecontents);
        edt_clolor = findViewById(R.id.edtClolor);
//        edt_notedate.setOnClickListener(this);
//        edt_notecontents.setOnClickListener(this);
//        edt_clolor.setOnClickListener(this);
        edt_notedate.setOnClickListener(this);
        edt_notetime.setOnClickListener(this);
        btn_DatePicker = findViewById(R.id.btn_DatePicker);
        btn_TimePicker = findViewById(R.id.btn_TimePicker);
        btn_ok = findViewById(R.id.btn_ok);
        btn_back = findViewById(R.id.btn_back);
        //按鈕行為設定
        btn_DatePicker.setOnClickListener(this);
        btn_TimePicker.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        colorSpinner();
    }

    private void colorSpinner(){
        spinner = findViewById(R.id.spinner);

        colorItems = new ArrayList<ColorItem>();
        colorItems.add(new ColorItem("Red","#ff0000"));
        colorItems.add(new ColorItem("Green","#00c7a4"));
        colorItems.add(new ColorItem("Blue","#4b7bd8"));
        colorItems.add(new ColorItem("Purple","#cf97ca"));
        String ww = edt_clolor.getText().toString();
        Log.i("DB_edt","ww"+edt_clolor.getText().toString());
        spinnerAdapter = new SpinnerAdapter(this,colorItems);
        spinner.setAdapter(spinnerAdapter);
//        if(bData.getString("type").equals("edit"))
//            spinner.setSelection(getPostiton(edt_clolor.getText().toString(),colorItems));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ColorItem colorItem= colorItems.get(position);
                edt_clolor.setText(colorItem.code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private int getPostiton(String findString,ArrayList<ColorItem> colorItems) {
        Log.i("DB_findString",findString.toString());
        int i;
        for (i = 0; i < colorItems.size(); i++) {
            ColorItem colorItem = colorItems.get(i);
            if (findString.equals(colorItem.code))
                break;
        }
        if (i == colorItems.size())
            return 0;
        else
            return i;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_DatePicker:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                edt_notedate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.btn_TimePicker:
                // Get Current Time
                final Calendar t = Calendar.getInstance();
                mHour = t.get(Calendar.HOUR_OF_DAY);
                mMinute = t.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                edt_notetime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                break;
            case R.id.btn_ok:
                new_notedate = edt_notedate.getText().toString();
                new_notetime = edt_notetime.getText().toString();
                new_notecontents = edt_notecontents.getText().toString();
                new_clolor = edt_clolor.getText().toString();
                dbAdapter = new DbAdapter(EditActivity.this);
                if(bData.getString("type").equals("add")){
                    try{
                        dbAdapter.createContacts(new_notedate,new_notetime,  new_notecontents, new_clolor);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        //回到列表
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    }
                }else{
                    try{
                        Log.i("DB_EditActivity","onClick1");
                        dbAdapter.updateContacts(String.valueOf(index), new_notedate, new_notetime, new_notecontents, new_clolor);
                        Log.i("DB_EditActivity","onClick2");
                    }catch(Exception e){
                        e.printStackTrace();
                    }finally {
                        Log.i("DB_EditActivity","onClick3");
                        Intent i = new Intent(this, ShowActivity.class);
                        i.putExtra("item_id",String.valueOf(index));
                        startActivity(i);
                    }
                }
                break;
            case R.id.btn_back:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }
}
