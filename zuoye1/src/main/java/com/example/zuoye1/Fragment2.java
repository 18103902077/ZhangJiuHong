package com.example.zuoye1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zuoye1.presenter.FoodPresenter;
import com.example.zuoye1.view.Iview;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements Iview {

    private View view;
    private RecyclerView recycler2;
    private RecycleAdapter recycleAdapter;
    private PopupWindow popupWindow;


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        initPresenter();
        initView(view);
        getPop();
        return view;
    }

    private void getPop() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.pop, null);
        popupWindow = new PopupWindow(view, 100, 100);
        TextView delete = view.findViewById(R.id.delete);
        TextView jump = view.findViewById(R.id.jump);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleAdapter.delete();
                popupWindow.dismiss();
            }
        });
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

    }

    private void initPresenter() {
        FoodPresenter foodPresenter = new FoodPresenter(this);
        foodPresenter.getStatus();
    }

    @Override
    public void getFoods(final List<DataBean> food) {
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recycleAdapter.getFoodLoad(food);
            }
        });*/
        recycleAdapter.getFoodLoad(food);
    }

    private void initView(View view) {
        recycler2 = (RecyclerView) view.findViewById(R.id.recycler2);
        recycler2.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleAdapter = new RecycleAdapter(getContext());
        recycler2.setAdapter(recycleAdapter);
    }
}
