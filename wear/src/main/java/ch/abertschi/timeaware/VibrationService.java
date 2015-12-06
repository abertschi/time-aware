package ch.abertschi.timeaware;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.text.format.Time;

import org.joda.time.LocalDateTime;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Andrin Bertschi
 */
public class VibrationService extends IntentService {

    public VibrationService() {
        super("TimeAwareActivity Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        long[] p = {0, 100, 100, 100, 100, 500};
        v.vibrate(p, -1);

//        long[] pattern = new long[50];
//        for (int i = 0; i < 50; i++) {
//            pattern[i] = ((i % 2) == 0 ) ? 10 : i;
//        }
//        v.vibrate(pattern, -1);

    }
}
