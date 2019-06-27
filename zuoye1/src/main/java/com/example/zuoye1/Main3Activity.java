package com.example.zuoye1;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private TextView title;
    private Toolbar tool;
    private ViewPager vp;
    private TabLayout tab;
    private NavigationView nav;
    private DrawerLayout dr;
    private Fragment4 fragment4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }
    private void initView() {
        title = (TextView) findViewById(R.id.title);
        tool = (Toolbar) findViewById(R.id.tool);
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);
        nav = (NavigationView) findViewById(R.id.nav);
        dr = (DrawerLayout) findViewById(R.id.dr);

        tool.setTitle("");
        setSupportActionBar(tool);
        tool.setLogo(R.mipmap.ic_launcher);
        tool.setNavigationIcon(R.mipmap.ic_launcher);
        tool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<Fragment> fragments = new ArrayList<>();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        fragment4 = new Fragment4();

        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(fragmentAdapter);

        tab.setupWithViewPager(vp);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                title.setText(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"列表");
        menu.add(1,2,2,"网格");
        menu.add(1,3,3,"瀑布");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                fragment4.recycler4.setLayoutManager(new LinearLayoutManager(this));
                break;
            case 2:
                fragment4.recycler4.setLayoutManager(new GridLayoutManager(this,2));
                break;
            case 3:
                fragment4.recycler4.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
