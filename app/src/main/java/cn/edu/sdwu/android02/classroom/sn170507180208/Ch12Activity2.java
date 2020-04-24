package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Ch12Activity2 extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private boolean bound;//判断是否解绑
    private MediaService mediaService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch12_2);
        bound=false;//一开始是未绑定状态

        //初始化服务连接
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //如果建立的连接自动调用本方法
                bound=true;
                MediaService.MyBinder myBinder=(MediaService.MyBinder)iBinder;
                mediaService=myBinder.getMediaService();

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                bound=false;
            }
        };
    }

    public void ch12_2_start(View view){
        //点击事件的处理
        startServiceClick(view);
    }
    public void ch12_2_pause(View view){
        //点击事件的处理
        startServiceClick(view);
    }
    public void ch12_2_stop(View view){
        //点击事件的处理
        startServiceClick(view);
    }
    public void ch12_2_stopservice(View view){
        //点击事件的处理
        startServiceClick(view);
    }

    public void startServiceClick(View view){
        int id=view.getId();
        Intent intent=new Intent(this,MyService.class);
        switch (id){
            case R.id.ch12_2_start:
                intent.putExtra("PlayerState","START");
                break;
            case R.id.ch12_2_pause:
                intent.putExtra("PlayerState","PAUSE");
                break;
            case R.id.ch12_2_stop:
                intent.putExtra("PlayerState","STOP");
                break;
            case R.id.ch12_2_stopservice:
                intent.putExtra("PlayerState","STOPSERVICE");
                break;
        }
        startService(intent);
    }

    public void bindClick(View view){
        int id=view.getId();//确定id 确认点击了哪一个按钮
        switch (id) {
            case R.id.ch12_2_bind:
                Intent intent=new Intent(this,MediaService.class);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
                break;
            case R.id.ch12_2_unbind:
                unbindService(serviceConnection);
                bound=false;
                break;
            case R.id.ch12_2_bindstart:
                if(bound){
                    mediaService.start();
                }
                break;
            case R.id.ch12_2_bindstop:
                if(bound){
                    mediaService.stop();
                }
                break;
            case R.id.ch12_2_bindpause:
                if(bound){
                    mediaService.pause();
                }
                break;
        }
    }

}
