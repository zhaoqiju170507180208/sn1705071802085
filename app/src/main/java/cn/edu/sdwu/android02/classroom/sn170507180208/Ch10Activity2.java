package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        }else if(requestCode==102){
            //从联系人列表返回结果
            if(requestCode==RESULT_OK){
                //得到联系人的信息(联系人的编号，lookup uri)
                String content=data.getDataString();
                Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
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

    //点击查看通讯录列表
    //需要在主配置文件中声明权限
    public void contactsList(View view){
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("content://contacts/people/"));
        startActivity(intent);
    }

    //查看联系人明细使用action—edit编辑
    public void contactsDetail(View view){
        Intent intent=new Intent(Intent.ACTION_EDIT);
        intent.setData(Uri.parse("content://contacts/people/1"));//数字代表第几个联系人
        startActivity(intent);
    }
    public void showMap(View view){
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("geo：50.123,7.1423"));//geo代表地图 数字是纬度 经度
        startActivity(intent);
    }
    public void showPhoto(View view){
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("content://media/external/images/media"));
        startActivity(intent);
    }

    //以子activity的形式，打开联系人列表，让用户选择一个联系人后，返回结果
    public void pickContact(View view){
        Intent intent=new Intent(Intent.ACTION_PICK);//隐式启动
        intent.setData(ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent,102);
    }
    //界面跳转 到activity1
    //在主配置文件中activity1中添加内容 与此处对应
    public void implictStart(View view){
        Intent intent=new Intent("com.inspur.action2");
        intent.setData(Uri.parse("abc://inspur.com"));
        startActivity(intent);
    }
}
