package com.example.zuoye1.model;

import android.util.Log;

import com.example.zuoye1.ApiService;
import com.example.zuoye1.CallBack;
import com.example.zuoye1.DataBean;
import com.example.zuoye1.FoodBean;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Foodmodel implements imodel {
    private static final String TAG = "tag";
    @Override
    public void getFoods(final CallBack callBack) {

            new Retrofit.Builder()
                    .baseUrl(ApiService.urlFoods)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class)
                    .getFoods()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<FoodBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(FoodBean foodBean) {
                            List<DataBean> data = foodBean.getData();
                            callBack.getFoods(data);

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "onError: =======food=====" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

    }
}
