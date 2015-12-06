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
public class TimeAwareService extends IntentService {

    private Timer mTimer = new Timer();

    public TimeAwareService() {
        super("TimeAwareActivity Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mTimer.scheduleAtFixedRate(new VibraTimer(this), 1000, 1000 * 60);
    }

    private static class VibraTimer extends TimerTask {

        private Context mContext;
        private Vibrator mVibrator;

        public VibraTimer(Context context){
            mContext = context;
            mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }

        @Override
        public void run() {
            LocalDateTime now = LocalDateTime.now();
            if ((now.getMinuteOfHour() % 5) == 0) {
                long[] pattern = new long[100];
                for (int i = 0; i < 50; i++) {
                    pattern[i] = ((i % 2) == 0 ) ? 10 : i;
                }
                mVibrator.vibrate(pattern, -1);
            }
        }
    }
}
