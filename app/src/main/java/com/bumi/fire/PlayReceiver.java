package com.bumi.fire;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class PlayReceiver extends BroadcastReceiver {

    private final String tag = "PlayReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        if(Intent.ACTION_MEDIA_BUTTON.equals(intentAction)){
            Log.v(tag, "Intent received");
            KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            Log.v(tag,"Key Code" + event.getKeyCode());

            //start activity
            Intent mIntent = new Intent(Intent.ACTION_MAIN);
            mIntent.setComponent(ComponentName.unflattenFromString("com.bumi.fire/.fire"));
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(mIntent);
        }
    }
}
