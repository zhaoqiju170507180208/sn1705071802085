package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Ch10Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch10_2);

    }

    public void  send_broadcast(View view){
        //发送广播
        Intent intent=new Intent("com.inspur.broadcast");//指定频道
        intent.putExtra("key1","message");

        sendBroadcast(intent);//发送
    }

    public void ch10Activity(View view){
        Intent intent=new Intent(this,Ch10Activity1.class);
        EditText editText=(EditText)findViewById(R.id.ch10_2_et);
        intent.putExtra("text",editText.getText().toString());//设置传递的数据
        startActivity(intent);
    }
    public void startSubActivity(View view){
        //1.以sub activity的方式启动子activity
        Intent intent=new Intent(this,Ch10Activity3.class);
        startActivityForResult(intent,101);
    }


}
