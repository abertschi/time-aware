package ch.abertschi.timeaware;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by abertschi on 06/12/15.
 */
public class ConfigurationService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().contains("/timeaware")) {
            Log.v("", "Reiceiving configuration data on " + messageEvent.getPath());
        }

        final String data = new String(messageEvent.getData());
        if (messageEvent.getPath().equals("/timeaware/frequency")) {
            Configurations.get(this).setFrequency(Long.valueOf(data));

        } else if (messageEvent.getPath().equals("/timeaware/enable")) {
            new VibrationScheduler().enableSchedule(this);

        } else if (messageEvent.getPath().equals("/timeaware/disable")) {
            new VibrationScheduler().disableSchedule(this);

        } else if (messageEvent.getPath().equals("/timeaware/demo")) {
            new VibrationScheduler().triggerOnce(this);
        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }
}
