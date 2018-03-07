package com.example.h.sabinsqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView dbTextView=null;
    private SQLiteDatabase db=null;
    private String path=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbTextView=(TextView)findViewById(R.id.textView);
        //open db
        try{
            path= Environment.getExternalStorageDirectory().getPath()+"/PeopleDB.db";
            Log.i("DB Work",path);
            db = SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.CREATE_IF_NECESSARY);

            Toast.makeText(MainActivity.this, "DB Open", Toast.LENGTH_SHORT).show();
        }catch(SQLiteAbortException e){
            Log.i("DB Work","DB cannot open at"+path);
        }

        String query="select * from people";
        Cursor c=null;

        try{
            c=db.rawQuery(query,null);
            Toast.makeText(MainActivity.this, "Cursor made", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e){
            Log.i("DB Work","DB cursor cannot make");
        }
        int nameColId=c.getColumnIndex("name");
        int phoneColId=c.getColumnIndex("phone");
        int addressColId=c.getColumnIndex("address");

        String dbText="DB Content\n";


        while(c.moveToNext()){
        String name=c.getString(nameColId);
            String phone=c.getString(phoneColId);
            String address=c.getString(addressColId);
            dbText+=dbText+name+"\n"+phone+"\n\n";
        }
        dbTextView.setText(dbText);

        db.close();
    }
}




