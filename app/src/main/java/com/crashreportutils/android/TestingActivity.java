package com.crashreportutils.android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;


public class TestingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        CrashReportApplication.init(TestingActivity.this);
        CrashUtils.crashApplication(2000);
    }
}
