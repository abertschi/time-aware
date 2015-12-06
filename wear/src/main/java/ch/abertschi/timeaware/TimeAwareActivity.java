package ch.abertschi.timeaware;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

/**
 * @author Andrin Bertschi
 */
public class TimeAwareActivity extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_aware);

        Configurations c = Configurations.get(this);
        if (c.isEnabled()) {
            Log.i("", "Vibra Schedule enabled.");
            new VibrationScheduler().enableSchedule(this);
        }
    }
}
