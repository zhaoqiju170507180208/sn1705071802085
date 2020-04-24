package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Ch13Activity1 extends AppCompatActivity {
    private EditText ip;
    private EditText port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch13_1);

        ip=(EditText)findViewById(R.id.ch13_1_ip);
        port=(EditText)findViewById(R.id.ch13_1_port);

        //将数据直接显示在页面中
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);
        ip.setText(sharedPreferences.getString("ip",""));
        port.setText(sharedPreferences.getString("port",""));
    }

    public void write(View view){
        EditText editText=(EditText)findViewById(R.id.c13_1_et);
        String content=editText.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("android02.txt", MODE_PRIVATE);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }
    }

    public void read(View view){
        try{
            FileInputStream fileInputStream=openFileInput("android02.txt");
            int size=fileInputStream.available();
            byte[] bytes=new byte[size];
            fileInputStream.read(bytes);//读取字符流
            String content=new String(bytes);//装换成字符串

            fileInputStream.close();
            Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.e(Ch13Activity1.class.toString(),e.toString());
        }
    }


    public void saveSharePref(View view){
        SharedPreferences sharedPreferences=getSharedPreferences("prefs",MODE_PRIVATE);//得到对象 括号内为1.文件名 以xml的形式保存
        SharedPreferences.Editor editor=sharedPreferences.edit();//编辑器
        //获取数据
        editor.putString("ip",ip.getText().toString());
        editor.putString("port",port.getText().toString());
        editor.commit();//存进去
    }


}
