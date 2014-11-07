package com.bumi.fire;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class OnTopService extends Service {

    private final static String tag = "fire:OnTopService";

    private PackageManager pm;
    private WindowManager windowManager;
    private ImageView logo;
    private String mPackageName;
    private String mForegroundTask;
    private  Timer mTimer=new Timer();

    TimerTask runner = new TimerTask() {
        @Override
        public void run() {
            debugShowTask();
            if (mForegroundTask.equals(getResources().getString(R.string.LauncherPackage))) {
                Log.v(tag, "overriding MediaControl");
                ((AudioManager) getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(getApplicationContext(), PlayReceiver.class));
            } else {
                Log.v(tag, "release MediaControl");
                ((AudioManager) getSystemService(AUDIO_SERVICE)).unregisterMediaButtonEventReceiver(new ComponentName(getApplicationContext(), PlayReceiver.class));
            }
        }
    };


    public OnTopService() {

    }

    @Override
    public void onCreate() {

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        logo = new ImageView(this);
        logo.setImageResource(R.drawable.flame);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.RIGHT;
        params.x = 15;
        params.y = 15;

        windowManager.addView(logo, params);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int starterId){
        mTimer.schedule(runner,100, 500);
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        windowManager.removeViewImmediate(logo);
        mTimer.cancel();
        ((AudioManager)getSystemService(AUDIO_SERVICE)).unregisterMediaButtonEventReceiver(new ComponentName(getApplicationContext(),PlayReceiver.class));
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void debugShowTask(){
        ActivityManager mAM = (ActivityManager)  this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo mTaskInfo;
        mTaskInfo = mAM.getRunningTasks(1).get(0);
        mForegroundTask = mTaskInfo.topActivity.getClassName();
        Log.v(tag,"Foreground Packagename: " + mForegroundTask);
    }

}
