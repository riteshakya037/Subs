package io.subs.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    SubscriptionAdaptor subscriptionAdaptor;
    @BindView(R.id.rv_subscription_list) RecyclerView rvSubscriptions;

    @Override protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        subscriptionAdaptor = new SubscriptionAdaptor(this, storageReference);
        rvSubscriptions.setAdapter(subscriptionAdaptor);
        rvSubscriptions.setLayoutManager(new LinearLayoutManager(this));
        rvSubscriptions.addItemDecoration(new TopPaddingDecoration(60));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("subs");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Subscription subscription = dataSnapshot.getValue(Subscription.class);
                subscriptionAdaptor.updateData(subscription);
            }

            @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }
}
