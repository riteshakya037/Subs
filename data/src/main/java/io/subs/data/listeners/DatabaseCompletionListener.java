package io.subs.data.listeners;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import io.reactivex.ObservableEmitter;
import io.reactivex.annotations.NonNull;
import io.subs.data.controllers.network.NetworkStateReceiver;
import java.util.Map;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class DatabaseCompletionListener
        implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static final String TAG = "DatabaseCompletion";
    private boolean isNetworkAvailable = true;

    @Inject
    public DatabaseCompletionListener(Context context, NetworkStateReceiver networkStateReceiver) {
        networkStateReceiver.addListener(this);
        context.registerReceiver(networkStateReceiver,
                new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public <T> void updateChildren(DatabaseReference databaseReference,
            @NonNull final ObservableEmitter<T> emitter, Map<String, Object> childUpdates) {
        if (isNetworkAvailable) {
            databaseReference.updateChildren(childUpdates, (databaseError, databaseReference1) -> {
                if (emitter.isDisposed()) {
                    return;
                }
                if (databaseError == null) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable(databaseError.getMessage()));
                }
            });
        } else {
            databaseReference.updateChildren(childUpdates);
            emitter.onComplete();
        }
    }

    @Override public void networkAvailable() {
        isNetworkAvailable = true;
        Log.e(TAG, "networkAvailable: ");
    }

    @Override public void networkUnavailable() {
        isNetworkAvailable = false;
        Log.e(TAG, "networkUnavailable: ");
    }
}
