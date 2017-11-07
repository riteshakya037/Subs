package io.subs.data.listeners;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.subs.domain.models.constants.Constants;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import org.json.JSONObject;

/**
 * @author Ritesh Shakya
 */

@SuppressWarnings("unchecked") public abstract class FirebaseValueListener<C>
        implements ValueEventListener {
    private static final String TAG = "FirebaseChildListener";
    private final Class<C> persistentClass;

    protected FirebaseValueListener() {
        this.persistentClass = (Class<C>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override public void onDataChange(DataSnapshot dataSnapshot) {
        onChildChanged(publishEvent(dataSnapshot));
    }

    private C publishEvent(DataSnapshot dataSnapshot) {
        HashMap<String, JSONObject> dataSnapshotValue =
                (HashMap<String, JSONObject>) dataSnapshot.getValue();
        String jsonString = new Gson().toJson(dataSnapshotValue);
        final Gson gson = new GsonBuilder().setDateFormat(Constants.DATE_FORMAT).create();
        C result = gson.fromJson(jsonString, persistentClass);
        Log.e(TAG, "onDataChange: " + result);
        return result;
    }

    public abstract void onChildChanged(C snapShot);
}
