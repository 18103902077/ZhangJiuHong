package com.example.zuoye1.presenter;


import com.example.zuoye1.CallBack;
import com.example.zuoye1.DataBean;
import com.example.zuoye1.model.Foodmodel;
import com.example.zuoye1.view.Iview;

import java.util.List;

public class FoodPresenter  implements Ipresenter{
    private final Foodmodel foodmodel;
    Iview iview;

    public FoodPresenter(Iview iview) {
        this.iview = iview;
        foodmodel = new Foodmodel();
    }

    @Override
    public void getStatus() {
        foodmodel.getFoods(new CallBack() {
            @Override
            public void getFoods(List<DataBean> food) {
                iview.getFoods(food);
            }
        });
    }
}
