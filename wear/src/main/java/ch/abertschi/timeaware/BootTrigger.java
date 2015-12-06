package ch.abertschi.timeaware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author Andrin Bertschi
 */
public class BootTrigger extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Configurations c = Configurations.get(context);
        if (c.isEnabled()) {
            Log.i("", "Vibra Schedule enabled.");
            new VibrationScheduler().enableSchedule(context);
        }
    }
}
