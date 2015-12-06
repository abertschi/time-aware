package ch.abertschi.timeaware;

import android.content.Context;
import android.content.SharedPreferences;

import wearprefs.WearPrefs;

/**
 * Created by abertschi on 06/12/15.
 */
public class Configurations {

    private static Configurations INSTANCE;

    public static final String PREFERENCE_ID = "timeaware_prefs";

    public static final String FREQUENCY = "frequency";

    public static final String ENABLED = "enabled";

    private Context mContext;

    private Configurations(Context c) {
        mContext = c;
    }

    public static synchronized Configurations get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Configurations(context);
        }
        return INSTANCE;
    }

    public SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(PREFERENCE_ID, 0);
    }

    public long getFrequency() {
       return getSharedPreferences().getLong(FREQUENCY, 60000);
    }

    public void setFrequency(long f) {
        getSharedPreferences().edit().putLong(FREQUENCY, f).commit();
    }

    public void setEnabled(boolean enabled) {
        getSharedPreferences().edit().putBoolean(ENABLED, enabled).commit();
    }

    public boolean isEnabled() {
        return getSharedPreferences().getBoolean(ENABLED, true);
    }
}
