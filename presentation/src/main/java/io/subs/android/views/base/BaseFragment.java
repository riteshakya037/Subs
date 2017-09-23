package io.subs.android.views.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import io.subs.android.di.HasComponent;
import io.subs.android.mvp.IPresenter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {
    private final Collection<IPresenter> mPresenters = new ArrayList<>();

    public BaseFragment() {
        setRetainInstance(true);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, fragmentView);
        initializeViews();
        return fragmentView;
    }

    protected abstract int getLayout();

    protected abstract void initializeViews();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.injectDagger();
    }

    protected abstract void injectDagger();

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override public void onResume() {
        super.onResume();
        for (IPresenter presenter : mPresenters) {
            presenter.onCreate();
        }
    }

    public void registerPresenter(@NonNull IPresenter... presenters) {
        Collections.addAll(mPresenters, presenters);
    }

    @Override public void onStart() {
        super.onStart();
        for (IPresenter presenter : mPresenters) {
            presenter.onStart();
        }
    }

    public Context context() {
        return this.getActivity().getApplicationContext();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked") protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
