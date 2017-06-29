package com.xp.lifehelper;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.xp.lifehelper.adapter.MainAdapter;
import com.xp.lifehelper.fragment.DefaultFragment;
import com.xp.lifehelper.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
    private Toolbar toolbar;
    private ViewPager viewpager;
    private List<Fragment> fragments;
    private MainAdapter adapter;
    private BottomNavigationBar bottomNavigationBar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        initFragmnets();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("首页");
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        drawer.addDrawerListener(mDrawerToggle);

        adapter=new MainAdapter(getSupportFragmentManager(),fragments);
        viewpager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mode:
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (currentNightMode) {
                    case Configuration.UI_MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        item.setTitle(R.string.theme_day);
                        break;
                    case Configuration.UI_MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        item.setTitle(R.string.theme_night);
                        break;
                }
                recreate();
                break;
                case R.id.setting:
                    Toast.makeText(MainActivity.this,"设置",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }



    private void initFragmnets() {
        fragments=new ArrayList<Fragment>();
        fragments.add(new NewsFragment());
        fragments.add(new DefaultFragment());
        fragments.add(new DefaultFragment());
        fragments.add(new DefaultFragment());
    }


}
