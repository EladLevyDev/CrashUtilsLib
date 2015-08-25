package com.crashreportutils.android;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CrashActivity extends ActionBarActivity {


    // Views
    private TextView textViewCrash;
    private TextView textView;
    private TextView textViewTitle;
    private Button btnSend;

    private String error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        initViews();

        String newError = getIntent().getStringExtra("error");
        if (newError != null && newError.length() > 0) {
            error = newError;
        }
        extractLogToFile(error);
        setOnClicks();

    }


    private void setOnClicks() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPickDialog userPickDialog =  UserPickDialog.newInstance(error);
                userPickDialog.show(getSupportFragmentManager(), "userpicker");
            }
        });
    }

    private void initViews() {
        textViewCrash = (TextView) findViewById(R.id.textViewCrash);
        textView = (TextView) findViewById(R.id.textView);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        String text = "<u>Device Info: </u>";
        textViewTitle.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        btnSend = (Button) findViewById(R.id.btnSend);
    }


    private String extractLogToFile(String error) {

        textView.setText(CrashUtils.getDeviceInfo(CrashActivity.this));

        try {
            if (error != null) {
                textViewCrash.setText(Html.fromHtml("<b> <u>Crash Info</u> </b> :" + "\n" + error));
            } else {
                textViewCrash.setText("Sorry, No crash log.");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        writeStringAsFile(CrashUtils.getDeviceInfo(CrashActivity.this) + "\n ___________Crash: \n" + error);
        return error;
    }

    public void writeStringAsFile(final String crash) {
        String path = Environment.getExternalStorageDirectory() + "/" + "MM_Crash_Logs/";

        Time time = new Time();
        time.setToNow();
        String nowInString = time.monthDay + ":" + time.month +":"+time.year + "_"+ time.hour + ":" + time.minute;
        String fullName = path + "crash_"+ nowInString.trim()+ "_.txt";

        // Extract to file.
        File file = new File (fullName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter (file);
            writer.write ("crash" + crash);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }



}