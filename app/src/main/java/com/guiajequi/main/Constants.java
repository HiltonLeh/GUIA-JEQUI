package com.guiajequi.main;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.guiajequi.MainActivity_TABSEG;
import com.guiajequi.R;

/**
 * Created by lesll on 22/08/2018.
 */

public class Constants {

    public static final String CHANNEL_ID = "mychannelid";
    public static final String CHANNEL_NAME = "mychannelname";
    public static final String CHANNEL_DESCRIPTION = "mychanneldescription";

    /**
     * Created by lesll on 22/08/2018.
     */
    
    public static class MyNotificationManager {
    
        private Context mCtx;
        private static MyNotificationManager mInstance;
    
        private MyNotificationManager(Context context){
            mCtx = context;
        }
    
        public static synchronized MyNotificationManager getInstance(Context context){
            if(mInstance == null){
                mInstance = new MyNotificationManager(context);
            }
            return mInstance;
        }
    
        public void displayNotification(String title, String body){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(title)
            .setContentText(body);
    
            Intent intent = new Intent(mCtx, MainActivity_TABSEG.class);
    
            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    
            mBuilder.setContentIntent(pendingIntent);
    
            NotificationManager mNotificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
    
            if(mNotificationManager!=null){
                mNotificationManager.notify(1, mBuilder.build());
            }
        }
    
    }
}
