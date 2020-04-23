package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Ch12Activity1 extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private MyService2 myService2;
    private boolean bindSucc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch12_1);
        bindSucc=false;
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //当调用者与服务建立起连接后，会自动调用本方法
                //第二个参数是service中onBind方法的返回值
                MyService2.MyBinder myBinder=(MyService2.MyBinder) iBinder;
                myService2=myBinder.getRandomService();
                bindSucc=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //当调用者与服务断开起连接后，会自动调用本方法
                bindSucc=false;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,MyService2.class);//实例化intent
        //绑定
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();

        //解绑
        unbindService(serviceConnection);
    }

    public void start_service(View view){
        //使用intent启动服务
        Intent intent=new Intent(this,MyService.class);
        //使用startservice以启动方式使用服务
        startService(intent);
    }
    public void stop_service(View view){
        //使用intent启动服务
        Intent intent=new Intent(this,MyService.class);
        //使用startservice以启动方式使用服务
        stopService(intent);
    }

    public void getRandom(View view){
        if(bindSucc){
            int ran=myService2.genRandom();
            Toast.makeText(this,ran+"",Toast.LENGTH_SHORT).show();
        }
    }
}
