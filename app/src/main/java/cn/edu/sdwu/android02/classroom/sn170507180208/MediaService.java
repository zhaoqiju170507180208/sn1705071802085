package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MediaService extends Service {
    private MediaPlayer mediaPlayer;
    private MyBinder myBinder;
    public MediaService() {
        
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=MediaPlayer.create(this,R.raw.wav);//获取到音频文件
        mediaPlayer.setLooping(false);//设置为不循环播放
        Log.i(MediaService.class.toString(),"onCreate");
    }
    //设置主要命令
    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        //从Intent中获取用户需要的操作，然后进一步处理
        Log.i(MediaService.class.toString(),"onStartCommand");
        String state=intent.getStringExtra("PlayerState");
        //判断用户要求
        if(state!=null){
            if(state.equals("START")){
                //播放 直接调用方法
                start();
            }
            if(state.equals("PAUSE")){
                //暂停
                pause();
            }
            if(state.equals("STOP")){
                //停止播放
                stop();
            }
            if(state.equals("STOPSERVICE")){
                //结束服务
                //不用写方法 直接使用stopSeif（）可以直接停止
                stopSelf();

            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    //开始播放方法
    public  void start(){
        mediaPlayer.start();
    }
    //暂停播放方法
    public void pause(){
        //判断是否在播放 如果在播放则暂停
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    //停止播放方法
    public void stop(){
        mediaPlayer.stop();
        //为了下一次的播放，需要调用prepare(准备)方法
        // 停止后再播放
        try {
            mediaPlayer.prepare();
        }catch (Exception e){
            Log.e(MediaService.class.toString(),e.toString());

        }

    }

    //使用完后销毁
    @Override
    public void onDestroy() {
        Log.i(MediaService.class.toString(),"onDestroy");
        mediaPlayer.stop();//停止服务
        mediaPlayer.release();//释放资源
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(MediaService.class.toString(),"onBind");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(MediaService.class.toString(),"onUnBind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{
        public MediaService getMediaService(){
            return  MediaService.this;
        }
    }
}
