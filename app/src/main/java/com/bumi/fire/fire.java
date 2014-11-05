package com.bumi.fire;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class fire extends Activity {

    private Intent mIntent;
    private final static String tag = "fire:";
    private AppAdapter mAdapter;
    private AppList mAppListObj;
    private Spinner mSpinner;
    private View mLastView;
    private List<ApplicationInfo> mApps;
    private ApplicationInfo mSelectedApp;
    private static String[] mBlacklist = {  "Android System",
                                            "com.amazon.acos.providers.UnifiedSettingsProvider",
                                            "Amazon GameCircle",
                                            "H2ClientService",
                                            "Bluetooth Share"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //((AudioManager)getSystemService(AUDIO_SERVICE)).registerMediaButtonEventReceiver(new ComponentName(getApplicationContext(),PlayReceiver.class));

        setContentView(R.layout.activity_fire);

        //Log.v(tag,"loading Background Image");
        // WallpaperManager mWallManager =  WallpaperManager.getInstance(getApplicationContext());
        //FrameLayout mFrame = (FrameLayout) findViewById(R.id.mainframe);
        /* doesnt work on AFTV and fails sometimes on the sim*/
        //mFrame.setBackground(mWallManager.getDrawable());

        mAppListObj = new AppList(this, mBlacklist);

        Log.d(tag,"building AppAdapter and View");

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mAdapter = new AppAdapter(this, mAppListObj.getCleanAppList(), mAppListObj);
        mSpinner.setAdapter(mAdapter);

        mIntent = new Intent(this, OnTopService.class);
        startService(mIntent);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mAppListObj.setSelectedAppID(i);
                mSelectedApp = mAppListObj.getSelectedApp(i);
                if(mSelectedApp != null) {
                    mIntent.removeExtra("app");
                    mIntent.putExtra("app",mSelectedApp.packageName);
                    startService(mIntent);

                }else{
                    mIntent.removeExtra("app");
                    mIntent.putExtra("app", "stopped");
                    startService(mIntent);
                }
                Log.v(tag, "selected Item Position " + i);
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
