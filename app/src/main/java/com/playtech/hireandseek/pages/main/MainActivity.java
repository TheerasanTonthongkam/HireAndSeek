package com.playtech.hireandseek.pages.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.playtech.hireandseek.R;
import com.playtech.hireandseek.customview.BottomNavigationViewEx;
import com.playtech.hireandseek.databinding.ActivityMainBinding;
import com.playtech.hireandseek.pages.ExpFragment;
import com.playtech.hireandseek.pages.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initBinding();
        initFragment();
        initBottomNavigation();
        initFirstTab();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
    }

    private void initBinding() {
        binding = DataBindingUtil.bind(getRootView());
    }

    private void initFragment() {
        fragments = new Fragment[5];
    }

    private void initBottomNavigation() {
        BottomNavigationViewEx bottomNavigation = binding.bottomNavigation;
        bottomNavigation.enableAnimation(false);
        bottomNavigation.enableShiftingMode(false);
        bottomNavigation.enableItemShiftingMode(false);
        bottomNavigation.setTextVisibility(true);
        bottomNavigation.setIconVisibility(true);
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnNavigationItemSelectedListener(this::navigationBottomListener);
    }

    private void initFirstTab() {
        fragments[0] = new ExpFragment();
        final FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragments[0]).commit();
    }

    private boolean navigationBottomListener(MenuItem item) {
        int id = item.getItemId();
        int index = 0;
        switch (id){
            case R.id.exp_item:
                if (fragments[0] == null)
                    fragments[0] = new ExpFragment();
                index = 0;
                break;
            case R.id.project_item:
                break;
            case R.id.profile_item:
                if (fragments[2] == null)
                    fragments[2] = new ExpFragment();
                index = 2;
                break;
            case R.id.award_item:
                break;
            case R.id.education_item:
                break;
        }

        final FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragments[index]).commit();

        return true;
    }
}
