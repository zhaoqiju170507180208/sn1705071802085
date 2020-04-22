package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startSubActivity(View v){
        //1.以sub activity的方式启动子activity
        Intent intent=new Intent(this,Ch10Activity3.class);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        //3.在父activity中获取返回值
        //requestCode用来区分从哪一个子activity返回的结果
        if(requestCode==101){
            if(requestCode==RESULT_OK){
                //用户点击的确认，进一步获取数据
                String name=data.getStringExtra("name");
                Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void web(View view){
        //使用隐式启动方式,打开网页
        // ACTION_VIEW最常用的动作：查看
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://baidu.com"));
        startActivity(intent);
    }

}
