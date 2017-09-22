package io.subs.android.views.screens.add_subscription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import io.subs.android.R;
import io.subs.android.di.components.DaggerSubscriptionComponent;
import io.subs.android.di.components.SubscriptionComponent;
import io.subs.android.views.DaggerBaseActivity;
import io.subs.domain.models.Subscription;

public class AddSubscriptionActivity extends DaggerBaseActivity<SubscriptionComponent>
        implements SubscriptionListFragment.SubscriptionListListener {
    private static final String TAG = "AddSubscriptionActivity";

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, AddSubscriptionActivity.class);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new SubscriptionListFragment());
        }
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        //addSubscriptionAdaptor = new AddSubscriptionAdaptor(this, storageReference);
        //rvSubscriptions.setAdapter(addSubscriptionAdaptor);
        //rvSubscriptions.setLayoutManager(new LinearLayoutManager(this));
        //rvSubscriptions.addItemDecoration(new TopPaddingDecoration(60));
        //DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("subs");
        //mDatabase.addChildEventListener(new ChildEventListener() {
        //    @Override public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        //        Subscription subscription = dataSnapshot.getValue(Subscription.class);
        //        addSubscriptionAdaptor.updateData(subscription);
        //    }
        //
        //    @Override public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        //
        //    }
        //
        //    @Override public void onChildRemoved(DataSnapshot dataSnapshot) {
        //
        //    }
        //
        //    @Override public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        //
        //    }
        //
        //    @Override public void onCancelled(DatabaseError databaseError) {
        //        Log.w(TAG, "Failed to read value.", databaseError.toException());
        //    }
        //});
    }

    @Override protected int getContextView() {
        return R.layout.activity_main;
    }

    @Override protected SubscriptionComponent getInjector() {
        return DaggerSubscriptionComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override public void onSubscriptionClicked(Subscription subscription) {
        Toast.makeText(this, subscription.getName(), Toast.LENGTH_LONG).show();
    }
}
