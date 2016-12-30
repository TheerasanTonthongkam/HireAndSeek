package com.playtech.hireandseek.pages.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.playtech.hireandseek.R;
import com.playtech.hireandseek.adapter.MainTabAdapter;
import com.playtech.hireandseek.customview.BottomNavigationViewEx;

public class TestActivity extends AppCompatActivity {

    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    CoordinatorLayout rootLayout;
    FloatingActionButton fabBtn;
    BottomNavigationViewEx bottomBar;

    AppBarLayout appBarLayout;
    private TabLayout mTabLayout;

    View gradientBg;

    boolean collapseState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initToolbar();
        initBottomBar();
        initInstances();
    }

    private void initBottomBar() {
        bottomBar = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);

        bottomBar.enableAnimation(false);
        bottomBar.enableShiftingMode(false);
        bottomBar.enableItemShiftingMode(false);
        bottomBar.setTextVisibility(true);
        bottomBar.setIconVisibility(true);
        bottomBar.setCurrentItem(1);
        bottomBar.getCurrentItem();

//
//        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
//        MainTabAdapter adapter = new MainTabAdapter(getSupportFragmentManager(), this);
//        viewPager.setAdapter(adapter);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initInstances() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerToggle = new ActionBarDrawerToggle(TestActivity.this, drawerLayout, R.string.hello_world, R.string.hello_world);
        drawerLayout.setDrawerListener(drawerToggle);

        gradientBg = (View) findViewById(R.id.gradient_bg);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rootLayout = (CoordinatorLayout) findViewById(R.id.rootLayout);

        fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "+66851334507";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);

            }
        });

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(" ");

        appBarLayout = (AppBarLayout) findViewById(R.id.AppToolBar);
        appBarLayout.addOnOffsetChangedListener(collapseToolBarListener());
    }

    private AppBarLayout.OnOffsetChangedListener collapseToolBarListener() {
        return new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (isCollapse(verticalOffset) && !collapseState) {
                    collapseState = true;
                    fadeInBackGround();
                }
                if (!isCollapse(verticalOffset) && collapseState) {
                    collapseState = false;
                    fadeOutBackGround();
                }
            }
        };
    }

    private void fadeInBackGround() {
        gradientBg.animate().alpha(1).setDuration(300).start();
    }

    private void fadeOutBackGround() {
        gradientBg.animate().alpha(0).setDuration(300).start();
    }

    private boolean isCollapse(int verticalOffset) {
        return verticalOffset + appBarLayout.getHeight() <= toolbar.getHeight() * 2;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
