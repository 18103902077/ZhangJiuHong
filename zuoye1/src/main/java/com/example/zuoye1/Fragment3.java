package com.example.zuoye1;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment3 extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * okUpLoad
     */
    private Button okUpLoad;
    /**
     * retrofit
     */
    private Button retrofit;
    /**
     * sevice
     */
    private Button sevice;
    /**
     * Button
     */
    private Button button4;
    /**
     * broad
     */
    private Button broad;
    private ImageView imageView;
    /**
     * TextView
     */
    private TextView textView;
    private static final String TAG = "tag";
    private MyLocalBroad myLocalBroad;
    private ProgressBar progressBar;

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment3, container, false);
        initView(view);
        initBroad();
        return view;
    }


    private void initBroad() {
        myLocalBroad = new MyLocalBroad();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pro");
        intentFilter.addAction("bro");
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(myLocalBroad, intentFilter);
    }

    class MyLocalBroad extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("pro".equals(action)) {
                final int progress = intent.getIntExtra("progress", 0);

                progressBar.setProgress(progress);

            }else {
                String name = intent.getStringExtra("name");
                NotificationManager systemService = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent1 = new Intent(getContext(), BroadActivity.class);
                intent1.putExtra("name",name);
                PendingIntent activity = PendingIntent.getActivity(getContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification build = new NotificationCompat.Builder(getContext())
                        .setAutoCancel(true)
                        .setContentTitle("广播")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(activity)
                        .build();
                systemService.notify(1,build);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(myLocalBroad);

    }

    private void initView(View view) {
        okUpLoad = (Button) view.findViewById(R.id.okUpLoad);
        okUpLoad.setOnClickListener(this);
        retrofit = (Button) view.findViewById(R.id.retrofit);
        retrofit.setOnClickListener(this);
        sevice = (Button) view.findViewById(R.id.sevice);
        sevice.setOnClickListener(this);
        broad = (Button) view.findViewById(R.id.broad);
        broad.setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (TextView) view.findViewById(R.id.textView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.okUpLoad:
                ok();
                break;
            case R.id.retrofit:
                retrofits();
                break;
            case R.id.sevice:
                Intent intent = new Intent(getContext(), MyService.class);
                getActivity().startService(intent);
            break;
            case R.id.broad:
                broad();
                break;
        }
    }

    private void broad() {
        Intent intent = new Intent();
        intent.setAction("bro");
        intent.putExtra("name","我是广播事件");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    private void retrofits() {
        String s = Environment.getExternalStorageDirectory() + File.separator + "123.jpg";
        File file = new File(s);
        if (!file.exists()) {
            Toast.makeText(getContext(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        RequestBody requestBody1 = RequestBody.create(MediaType.parse("text/plain"), "1811a");

        new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiService.urlretrofit)
                .build()
                .create(ApiService.class)
                .getRetrofit(requestBody1, part)
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        final String s1 = response.body().toString();
                        Log.d(TAG, ": =======re=======" + s1);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(s1);
                            }
                        });

                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                        Log.d(TAG, "onFailure: =======re=======" + t.getMessage());
                    }
                });

    }

    private void ok() {
        String s = Environment.getExternalStorageDirectory() + File.separator + "123.jpg";
        File file = new File(s);
        if (!file.exists()) {
            Toast.makeText(getContext(), "文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody build = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "1811a")
                .addFormDataPart("file", file.getName(), requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build1 = new Request.Builder()
                .post(build)
                .url("http://yun918.cn/study/public/file_upload.php")
                .build();
        okHttpClient.newCall(build1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, ": ========ok=======" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.d(TAG, "onFailure: ========ok=======" + string);
                try {

                    JSONObject jsonObject = new JSONObject(string);
                    JSONObject data = jsonObject.getJSONObject("data");
                    final String url = data.optString("url");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getContext()).load(url).into(imageView);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
