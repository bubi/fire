package com.bumi.fire;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.KeyEvent;

public class PlayReceiver extends BroadcastReceiver {

    private final String tag = "PlayReceiver";
    public static final String PREFS_NAME = "firePrefs";

    private PackageManager mPM;


    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        String mPackage;

        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        mPackage = settings.getString("app","stopped");

        if(Intent.ACTION_MEDIA_BUTTON.equals(intentAction)){
            Log.v(tag, "Intent received");
            KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            Log.v(tag,"Key Code" + event.getKeyCode());

            //start activity
            Log.v(tag,"Launch Package Name; " + mPackage);
            if(!mPackage.equals("stopped")){
                Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(mPackage);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(mIntent != null) context.startActivity(mIntent);
            }
        }
    }
}
