package com.example.zuoye1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment4 extends Fragment {
    private View view;
    public RecyclerView recycler4;
    private RecycleAdapter recycleAdapter;

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment4, container, false);
        initView(view);
        getSqlData();
        return view;
    }

    private void getSqlData() {
        List<DataBean> dataBeans = Utile.queryAll();
        recycleAdapter.getFoodLoad(dataBeans);
    }

    private void initView(View view) {
        recycler4 = (RecyclerView) view.findViewById(R.id.recycler4);
        recycler4.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleAdapter = new RecycleAdapter(getContext());
        recycler4.setAdapter(recycleAdapter);

        registerForContextMenu(recycler4);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&&recycleAdapter!=null){
            getSqlData();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1,1,1,"删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        recycleAdapter.deleteSql();
        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
        return super.onContextItemSelected(item);
    }
}
