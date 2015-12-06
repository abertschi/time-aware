package ch.abertschi.timeaware;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;

import wearprefs.WearPrefs;

/**
 * Created by abertschi on 06/12/15.
 */
public class VibrationScheduler extends WakefulBroadcastReceiver {

    private AlarmManager mAlarmManager;

    private PendingIntent mVibrationIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, VibrationService.class);
        startWakefulService(context, service);
    }

    public void triggerOnce(Context context) {
        Intent intent = new Intent(context, VibrationScheduler.class);
        context.sendBroadcast(intent);
    }

    public void enableSchedule(Context context) {
        mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, VibrationScheduler.class);
        mVibrationIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        long triggerMillis = cal.getTimeInMillis();

        long interval = Configurations.get(context).getFrequency();
        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerMillis, interval, mVibrationIntent);
    }

    public void disableSchedule(Context context) {

        Intent intent = new Intent(context, VibrationScheduler.class);
        mVibrationIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (mAlarmManager != null) {
            mAlarmManager.cancel(mVibrationIntent);
        }
    }

}
