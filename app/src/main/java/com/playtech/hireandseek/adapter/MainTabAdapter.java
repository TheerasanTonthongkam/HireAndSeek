package com.playtech.hireandseek.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.playtech.hireandseek.R;

public class MainTabAdapter extends FragmentPagerAdapter {

    public final int PAGE_COUNT = 3;

    private final String[] mTabsTitle = {"EXP", "Projects", "Profile", "Awards", "Education"};
    private int[] mTabsIcons = {
            R.drawable.exp,
            R.drawable.project,
            R.drawable.user,
            R.drawable.award,
            R.drawable.education,
    };
    private Context context;

    public MainTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(mTabsTitle[position]);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(mTabsIcons[position]);

        return view;
    }

    @Override
    public Fragment getItem(int pos) {
        switch (pos) {
            case 0:
                return new Fragment();
            case 1:
                return new Fragment();
            case 2:
                return new Fragment();
            case 3:
                return new Fragment();
            case 4:
                return new Fragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabsTitle[position];
    }
}