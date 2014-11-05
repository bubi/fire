package com.bumi.fire;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class OnTopService extends Service {

    private PackageManager pm;

    private WindowManager windowManager;
    private ImageView logo;

    private String mAppName;

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

        String mTmp;
        mTmp = intent.getStringExtra("app");
        if(mTmp != null) {
            if (mTmp.equals("stopped")) {
                stopSelf();
            } else {
                startApp(intent.getStringExtra("app"));

            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void startApp(String packageName){
        pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        if(intent != null) startActivity(intent);
    }

}
