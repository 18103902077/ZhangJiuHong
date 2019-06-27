package com.example.zuoye1;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyService extends Service {
    private static final String TAG = "tag";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(){
            @Override
            public void run() {
                super.run();
                broad();
            }
        }.start();
    }

    private void broad() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://yun918.cn/study/public/res/UnknowApp-1.0.apk")
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: =====broad======"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                long l = response.body().contentLength();

                String s = Environment.getExternalStorageDirectory() + File.separator + "a.apk";
                File file = new File(s);
                if (!file.exists()){
                    file.createNewFile();
                }

                FileOutputStream outputStream = new FileOutputStream(file);
                int len=0;
                int curr=0;
                byte[] bytes=new byte[1024*8];
                while ((len=inputStream.read(bytes))!=-1){
                    curr+=len;
                    outputStream.write(bytes,0,len);

                    int progress = (int) (curr * 100 / l);

                    Intent intent = new Intent();
                    intent.setAction("pro");
                    intent.putExtra("progress",progress);
                    LocalBroadcastManager.getInstance(MyService.this).sendBroadcast(intent);
                }
                outputStream.close();
                inputStream.close();
            }
        });
    }
}
