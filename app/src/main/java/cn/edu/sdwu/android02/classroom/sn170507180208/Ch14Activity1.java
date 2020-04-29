package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Ch14Activity1 extends AppCompatActivity {
    //创建成员引用 myopenhelper
    private MyOpenHelper myOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch14_1);

        //实例化
        myOpenHelper=new MyOpenHelper(this);
        //创建方法 getWritableDatabase() 可写方式打开数据库 如果数据库不存在，自动创建数据库
        //SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        //使用完毕后，把数据库关闭
       // sqLiteDatabase.close();
    }
public void insert(View view){
    SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
    try{
        //将插入的数据放入ContentValues中
        //事务的处理
        sqLiteDatabase.beginTransaction();//开始事务
        ContentValues contentValues=new ContentValues();
        contentValues.put("stuname","JOY");
        contentValues.put("stutel","15222222222");
        sqLiteDatabase.insert("student",null,contentValues);
        sqLiteDatabase.setTransactionSuccessful();//当所有操作都完成后，调用此方法，才会将数据真正保存到数据库中

    }catch (Exception e){
        Log.e(Ch14Activity1.class.toString(),e.toString());
    }finally {
        sqLiteDatabase.endTransaction();//结束事务
        sqLiteDatabase.close();//使用完毕关闭数据库

    }
}
    public void query(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            Cursor cursor=sqLiteDatabase.rawQuery("select * from student where stuname=?",new String[]{"Jackson YEE"});
            //Cursor游标 每次都向下变动一个
            while(cursor.moveToNext()){
                int id=cursor.getInt(cursor.getColumnIndex("id"));//cursor.getColumnIndex获取列的索引
                String stuname=cursor.getString(cursor.getColumnIndex("stuname"));
                String stutel=cursor.getString(cursor.getColumnIndex("stutel"));
                Log.i(Ch14Activity1.class.toString(),"id:"+id+",stuname:"+stuname+",stutel:"+stutel);
            }
            cursor.close();
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.close();//使用完毕关闭数据库

        }
    }
    public void delete(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            sqLiteDatabase.beginTransaction();//开始事务
            sqLiteDatabase.delete("student","id=?",new String[]{"1"});
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();//使用完毕关闭数据库
        }
    }
    public void edit(View view){
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            sqLiteDatabase.beginTransaction();//开始事务

            ContentValues contentValues=new ContentValues();
            contentValues.put("stutel","15269186666");

            sqLiteDatabase.update("student",contentValues,"id=?",new String[]{"2"});
            sqLiteDatabase.setTransactionSuccessful();
        }catch (Exception e){
            Log.e(Ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();
            sqLiteDatabase.close();//使用完毕关闭数据库
        }
    }

}
