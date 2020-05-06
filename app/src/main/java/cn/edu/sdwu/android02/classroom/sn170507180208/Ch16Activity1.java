package cn.edu.sdwu.android02.classroom.sn170507180208;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.Arrays;

public class Ch16Activity1 extends AppCompatActivity {
    private TextureView textureView;
    private SurfaceTexture surfaceTexture;
    private CameraDevice.StateCallback stateCallback;
    private CameraDevice cameraDevice;
    private
    private CaptureRequest.Builder captureRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查相机的使用权限
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //1：判断当前用户是否授权过
            int result=checkSelfPermission(Manifest.permission.CAMERA);
            if (result== PackageManager.PERMISSION_GRANTED){

            }else{
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},104);
            }
        }
        //实例化StateCallback，当用它来打开摄像机时，执行的方法（便于我们进行会话的创建）
        stateCallback=new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                Ch16Activity1.this.cameraDevice=cameraDevice;
                //准备预览时使用的组件
                Surface surface=new Surface(surfaceTexture);
                try{
                    //创建一个捕捉请求CaptureRequest
                    CaptureRequest.Builder captureRequestBuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    captureRequestBuilder.addTarget(surface);
                    //创剪一个机会捕捉会话
                    //参数1代表后续预览或拍照使用的组件
                    //参数2代表的是监听器，创建会话完成后执行的方法
                    cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            //会话完成创建时，我们可以在参数中的到会话的对象
                            //开始相机的预览
                            try{
                                previewRequest=captureRequestBuilder.build();
                                cameraCaptureSession.setRepeatingRequest(previewRequest,null,null));
                            }catch (Exception e){
                                Log.e(Ch16Activity1.class.toString(),e.toString());
                            }




                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    },null);
                }catch (Exception e){
                    Log.e(Ch16Activity1.class.toString(),e.toString());
                }

            }

            @Override
            public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                Ch16Activity1.this.cameraDevice=null;
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

            }
        }

    }
    public void openCamera(int width,int height){
        CameraManager cameraManager=(CameraManager)getSystemService(CAMERA_SERVICE);
        try{
            cameraManager.openCamera("0");//0代表后置摄像头 1代表前置摄像头
            }catch (Exception e){
            Log.e(Ch16Activity1.class.toString(),e.toString());
        }
    }

    private  void setCameraLayout(){
        //用户授权后，加载界面
        setContentView(R.layout.layout_ch16_1);
        textureView=(TextureView)findViewById(R.id.ch16_tv);
        //当textureview准备好之后自动调用setSurfaceTextureListener的监听器
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                //主要使用此方法  （可用）
                //当可用时，打开摄像头
                Ch16Activity1.this.surfaceTexture=surfaceTexture;
                openCamera(width,height);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {
                //改变大小
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                //销毁
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                //更新
            }
        });
    }
    public void call(View view){
        ////判断当前手机系统版本是否是6.0之后的系统(Build.VERSION.SDK_INT)
        //M=23代表6.0
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //1：判断当前用户是否授权过
            int result=checkSelfPermission(Manifest.permission.CALL_PHONE);
            if (result== PackageManager.PERMISSION_GRANTED){
                callphone();
            }else{
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},101);
            }
        }
    }
    public void chgOri(View view){
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_);
    }
    public void sms(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            //1：判断当前用户是否授权过
            int result=checkSelfPermission(Manifest.permission.SEND_SMS);
            if (result== PackageManager.PERMISSION_GRANTED){
                sendSms();
            }else{
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},102);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==101){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
             callphone();
            }
        }
        if (requestCode==102){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                sendSms();
            }
        }
        if (requestCode==104){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
        }
    }

    public void sendSms(){
        //借助于短信管理器 SmsManager工具进行发送
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage("1330511234","13111112222","short message test",null,null);
    }
    public void callphone(){
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel://13956789999"));
        startActivity(intent);
    }
}
