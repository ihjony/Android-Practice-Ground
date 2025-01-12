package com.example.undefined.jobintentservicedemo;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

public class ExampleJobIntentService extends JobIntentService {
    private static final String TAG="ExampleJobIntentService";

    public static void enqueueWork(Context context, Intent intent) {
            enqueueWork(context,ExampleJobIntentService.class,123,intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d(TAG,"onHandleWork");
        String input=intent.getStringExtra("inputExtra");
        for (int i = 0; i <10 ; i++) {
            Log.d(TAG,input+" - "+i);
            if(isStopped()){
                return;
            }
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
    }

    @Override
    public boolean onStopCurrentWork() {
        Log.d(TAG,"onStopCurrentWork");
        return super.onStopCurrentWork();

    }
}
