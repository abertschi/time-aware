package ch.abertschi.timeaware;

import android.app.Notification;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class TimeAwareSettings extends PreferenceActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);

        apiClient = new GoogleApiClient.Builder(this)
            .addApi(Wearable.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build();

        Preference demo = (Preference)findPreference("demo");
        demo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new SendToWearThread("/timeaware/demo", "").start();
                return true;
            }
        });

        Preference enable = (Preference)findPreference("enabled");
        enable.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getSharedPreferences().getBoolean(preference.getKey(), true)) {
                    new SendToWearThread("/timeaware/enable", "").start();
                } else {
                    new SendToWearThread("/timeaware/disable", "").start();
                }
                return true;
            }
        });

        Preference frequency = (Preference)findPreference("frequency");
        frequency.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new SendToWearThread("/timeaware/frequency", preference.getSharedPreferences().getString("frequency", "60000"));
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        if (apiClient != null && apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    class SendToWearThread extends Thread {

        private String mPath;
        private String mMessage;

        public SendToWearThread(String path, String message) {
            mPath = path;
            mMessage = message;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(apiClient).await();
            for(Node n: nodes.getNodes()) {
                MessageApi.SendMessageResult result =
                        Wearable.MessageApi.sendMessage(apiClient, n.getId(), mPath, mMessage.getBytes()).await();

                if (result.getStatus().isSuccess()) {
                    Log.i("", String.format("Message %s successfully sent to node %s", mPath, n.getId()));
                }
                else {
                    Log.e("", String.format("Could not send message %s to node %s", mPath, n.getId()));
                }
            }
        }

    }
}
