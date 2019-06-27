package com.example.zuoye1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment {

    private View view;
    private RecyclerView recycler1;
    private RecycleAdapter recycleAdapter;
    private static final String TAG = "tag";
    int page = 1;
    private SmartRefreshLayout smart;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        initView(view);
        getBanner();
        getFood();
        return view;
    }
    private void getBanner() {
        new Retrofit.Builder()
                .baseUrl(ApiService.urlBanner)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getBanners()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        List<BannerBean.DataBean> data = bannerBean.getData();
                        recycleAdapter.getBanner(data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: =======ban=====" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void getFood() {
        new Retrofit.Builder()
                .baseUrl(ApiService.urlFood)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getFoods(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FoodBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodBean foodBean) {
                        List<DataBean> food = foodBean.getData();
                        if (page == 1) {
                            recycleAdapter.getFoodRefresh(food);
                            smart.finishRefresh();
                        } else {
                            recycleAdapter.getFoodLoad(food);
                            smart.finishLoadmore();
                        }

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

    private void initView(View view) {
        recycler1 = (RecyclerView) view.findViewById(R.id.recycler1);
        recycler1.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleAdapter = new RecycleAdapter(getContext());
        recycler1.setAdapter(recycleAdapter);
        registerForContextMenu(recycler1);


        smart = (SmartRefreshLayout) view.findViewById(R.id.smart);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page=1;
                getFood();
            }
        });
        smart.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getFood();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1,1,1,"删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        recycleAdapter.delete();
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
}
