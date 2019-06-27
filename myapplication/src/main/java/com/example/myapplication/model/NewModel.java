package com.example.myapplication.model;

import android.util.Log;

import com.example.myapplication.CallBack;
import com.example.myapplication.NewBean;
import com.example.myapplication.RecentBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewModel implements Imod {
    private static final String TAG = "tag";

    @Override
    public void getUrl(int page, final CallBack callBack) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder()
                .url("http://news-at.zhihu.com/api/"+page+"/news/hot")
                .build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ========="+e.getMessage());
                callBack.getError(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                NewBean newBean = new Gson().fromJson(string, NewBean.class);
                List<RecentBean> recent = newBean.getRecent();
                callBack.getNew(recent);
            }
        });

    }
}
