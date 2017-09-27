package io.subs.data.listeners;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import io.reactivex.ObservableEmitter;

/**
 * @author Ritesh Shakya
 */

public class DatabaseCompletionListener<C> implements DatabaseReference.CompletionListener {
    private ObservableEmitter<C> emitter;

    public DatabaseCompletionListener(ObservableEmitter<C> emitter) {
        this.emitter = emitter;
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (emitter.isDisposed()) {
            return;
        }
        if (databaseError == null) {
            emitter.onComplete();
        } else {
            emitter.onError(new Throwable(databaseError.getMessage()));
        }
    }
}
