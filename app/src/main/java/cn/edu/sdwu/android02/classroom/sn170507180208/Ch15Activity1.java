package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Ch15Activity1 extends AppCompatActivity {
private ContentResolver contentResolver;
    private static final String CONTENT_URI_STRING="content://com.inspur.android02/student";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch15_1);
        contentResolver=getContentResolver();
    }
    public void query(View view){
      String[] selColums={"id","stuname","stuadd","stutel"};
        Uri uri=Uri.parse("content://com.inspur.android02/student");
        Cursor cursor=contentResolver.query(uri,selColums,null,null,null);
        while(cursor.moveToNext()){
            Log.i(Ch15Activity1.class.toString(),cursor.getString(cursor.getColumnIndex("stuname")));
        }
        cursor.close();
    }

    public void insert(View view){
        ContentValues contentValues=new ContentValues();
        contentValues.put("stuname","tom");
        contentValues.put("stutel","13344445555");
        contentValues.put("stuadd","add");

        contentResolver.insert(Uri.parse(CONTENT_URI_STRING),contentValues);
    }

    public void delete(View view){
        Uri uri=Uri.parse(CONTENT_URI_STRING+"/2");//只删除id=2的
        contentResolver.delete(uri,null,null);
    }

    public void modify(View view){
        ContentValues contentValues=new ContentValues();
        contentValues.put("stuname","John");

        Uri uri=Uri.parse(CONTENT_URI_STRING+"/3");
        contentResolver.update(uri,contentValues,null,null);
    }
}
