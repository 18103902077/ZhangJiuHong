package com.example.zuoye1;

import android.content.Context;
import android.widget.Toast;


import com.example.zuoye1.db.DaoSession;
import com.example.zuoye1.db.DataBeanDao;

import java.util.List;

public class Utile {
    public static DataBean queryItem(DataBean dataBean){
        DaoSession daoSession = MyApplication.getDaoSession();
        DataBean unique = daoSession.queryBuilder(DataBean.class)
                .where(DataBeanDao.Properties.Title.eq(dataBean.getTitle()))
                .build().unique();
        return unique;
    }

    public static void delete(DataBean dataBean){
        DaoSession daoSession = MyApplication.getDaoSession();
        DataBean dataBean1 = queryItem(dataBean);
        if (dataBean1!=null){
            daoSession.delete(dataBean);
        }
    }

    public static void insert(Context context,DataBean dataBean){
        DaoSession daoSession = MyApplication.getDaoSession();
        DataBean dataBean1 = queryItem(dataBean);
        if (dataBean1==null){
            daoSession.insert(dataBean);
            Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "已存在", Toast.LENGTH_SHORT).show();
        }
    }

    public static List<DataBean> queryAll(){
        DaoSession daoSession = MyApplication.getDaoSession();
        List<DataBean> dataBeans = daoSession.loadAll(DataBean.class);
        return dataBeans;
    }


}
