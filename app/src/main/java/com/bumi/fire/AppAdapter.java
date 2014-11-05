package com.bumi.fire;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Michael Bubestinger on 28.10.2014.
 */
public class AppAdapter extends BaseAdapter {

    private Context mContext;
    private List<ApplicationInfo> mAppList;
    private ApplicationInfo  mApp;
    private AppList mAppObj;

    public AppAdapter(Context context, List<ApplicationInfo> apps, AppList list){
        this.mContext = context;
        this.mAppList = apps;
        this.mAppObj = list;
    }

    public void updateAppAdapterList(List<ApplicationInfo> apps){
        this.mAppList = apps;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        }

        ImageView mAppImage = (ImageView) convertView.findViewById(R.id.gridImage);
        TextView  mAppName  = (TextView) convertView.findViewById(R.id.gridText);

        mApp = getItem(position);

        if(mApp != null) {
            mAppName.setText(mApp.loadLabel(mContext.getPackageManager()));
            mAppImage.setImageDrawable(mApp.loadIcon(mContext.getPackageManager()));
        }else{
            mAppName.setText("Stop Service");
            mAppImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        }
        return convertView;
    }

    @Override
    public int getCount(){
        return mAppList.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public ApplicationInfo getItem(int position){
        return mAppList.get(position);
    }


}
