package com.example.zuoye1;

import android.app.Application;


import com.example.zuoye1.db.DaoMaster;
import com.example.zuoye1.db.DaoSession;

public class MyApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "my.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();

    }

    public static DaoSession getDaoSession(){
        return daoSession;
    }
}
