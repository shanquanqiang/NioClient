package com.sqq.nioclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sqq.nioclient.nioclient.NioClient;

/**
 * Created by sqq on 2016/7/30.
 */
public class TcpService extends Service{
    final String TAG = "NioClient-TcpService";
    private static final Object monitor = new Object();

    /**
     * 端口号1~1023是保留的，1024~65535是用户自定义
     */
    int port = 13448;

    String ip = "10.0.3.114";

    private static NioClient client;

    public static NioClient getInstance(){
        synchronized (monitor) {
            return client;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    /**
     * startService一次就启动一次线程
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if(client!=null && !client.isClientClosed()){
            client.closeNioClient();
        }
        client = new NioClient.Builder()
                .setTcpPort(port)
                .setHost(ip)
                .build();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "destory");
        client.closeNioClient();
    }



}
