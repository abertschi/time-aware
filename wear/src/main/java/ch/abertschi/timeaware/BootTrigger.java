package ch.abertschi.timeaware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;

/**
 * @author Andrin Bertschi
 */
public class BootTrigger extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent timeAware = new Intent(context, TimeAwareService.class);
        context.startService(timeAware);
    }
}
