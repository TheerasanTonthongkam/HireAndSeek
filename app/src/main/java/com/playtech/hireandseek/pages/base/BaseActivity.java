package com.playtech.hireandseek.pages.base;

import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class BaseActivity extends AppCompatActivity {

    protected ViewGroup getRootView() {
        return (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }
}
