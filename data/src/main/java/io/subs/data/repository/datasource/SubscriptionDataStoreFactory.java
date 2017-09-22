package io.subs.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Factory that creates different implementations of {@link SubscriptionDataStore}.
 */
@Singleton public class SubscriptionDataStoreFactory {

    private final Context context;

    @Inject SubscriptionDataStoreFactory(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Create {@link SubscriptionDataStore} to retrieve data from the Cloud.
     */
    public SubscriptionDataStore createCloudDataStore() {
        return new FirebaseSubscriptionDataStore();
    }
}
