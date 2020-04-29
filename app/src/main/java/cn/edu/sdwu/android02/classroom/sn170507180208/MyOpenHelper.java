package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Thinkpad on 2020/4/29.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    //创建MyOpenHelper构造方法
    private String STUDENT_TB_SQL="create table student(id integer primary key autoincrement,stuname text,stutel text)";

    // 创建数据库
    public MyOpenHelper(Context context){
        //调用父类的构造方法
        // super(Context context上下文, String name创建的数据库名, CursorFactory factory传入null, int version数据库的版本)
        super(context,"stud.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //当打开数据库发现数据库并不存在，此时会自动创建数据库，然后调用onCreate方法
        //在本方法中完成数据库对象（表，视图，索引等）的创建

        //通过SQLiteDatabase对象，完成sql的执行
        sqLiteDatabase.execSQL(STUDENT_TB_SQL);
        Log.i(MyOpenHelper.class.toString(),"onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
