package com.crashreportutils.android;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CrashReportApplication extends Application
{

  public void onCreate ()
  {
    super.onCreate();
    // Setup handler for uncaught exceptions.
    Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
    {
      @Override
      public void uncaughtException (Thread thread, Throwable e)
      {
        handleUncaughtException (thread, e, getApplicationContext());
      }
    });
  }

    public static void init(final Context context){
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread thread, Throwable e) {
                    handleUncaughtException(thread, e, context);
                }
            });
    }

  public static void handleUncaughtException (Thread thread, Throwable e, final Context context)
  {
      e.printStackTrace(); // not all Android versions will print the stack trace automatically
      StringWriter stackTrace = new StringWriter();
      e.printStackTrace(new PrintWriter(stackTrace));

      StringBuilder errorReport = new StringBuilder();
      errorReport.append(stackTrace.toString());
      Log.e("Crash", "Crash info:", e);

      Intent intent = new Intent ();
    intent.setAction ("com.crashreportutils.android.SEND_LOG"); // see step 5.
      intent.putExtra("error", errorReport.toString());
    intent.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
      context.startActivity(intent);

    System.exit(1); // kill off the crashed app
  }
}