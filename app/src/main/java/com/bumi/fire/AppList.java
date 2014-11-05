package com.bumi.fire;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Bubestinger on 30.10.2014.
 */
public class AppList{

    private final static String tag = "fire.AppList: ";
    private Context mContext;
    private PackageManager mPM;
    private List<ApplicationInfo> mInstalledApps;
    private List<ApplicationInfo> mAppList = new ArrayList<ApplicationInfo>();
    private String[] mBacklisted;
    private int mSelected;



    public AppList(Context context){
        this.mContext = context;
        this.mBacklisted = null;
        mPM = mContext.getPackageManager();
        mInstalledApps =  mPM.getInstalledApplications(0);
        updateAppList(mInstalledApps, mBacklisted);
    }

    public AppList(Context context, String[] blacklist){
        this.mContext = context;
        this.mBacklisted = blacklist;
        mPM = mContext.getPackageManager();
        mInstalledApps =  mPM.getInstalledApplications(0);
        updateAppList(mInstalledApps, mBacklisted);
    }

    /* returns a List without blacklisted Apps */
    public List<ApplicationInfo> getCleanAppList(){
        return mAppList;
    }

    public List<ApplicationInfo> removeApp(ApplicationInfo app){
        /* TODO implement remove ITEM*/
        return mAppList;
    }

    public List<ApplicationInfo> moveAppAfter(ApplicationInfo app, ApplicationInfo after){
        /* TODO implement moveAppAfter */
        return mAppList;
    }

    public ApplicationInfo getSelectedApp(int id){
        return mAppList.get(id);
    }

    public boolean isSelectedApp(int id){
        if(mSelected == id) return true;
        else return false;
    }

    public void setSelectedAppID(int id){
        mSelected = id;
    }

    public int getSelectedAppID(){
        return mSelected;
    }

    private void updateAppList(List<ApplicationInfo> list, String[] blacklist){

        String tmp;
        Boolean res;
        ApplicationInfo nothing = null;

        mAppList.clear();
        mAppList.add(nothing);
        for(ApplicationInfo app : list){
            mAppList.add(app);
            Log.v(tag, "added: " + app.loadLabel(mContext.getPackageManager()));
            tmp = app.loadLabel(mContext.getPackageManager()).toString();
            for(String name : blacklist){
                   /* not sure if equal() will work */
                res = name.equals(tmp);
                if(res){
                    mAppList.remove(app);
                    Log.v(tag,"blacklisted: " + app.loadLabel(mContext.getPackageManager()) );
                }
            }
        }
    }
}
