package com.riteshakya.subs.views.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.riteshakya.subs.di.HasComponent;
import com.riteshakya.subs.mvp.IPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import butterknife.ButterKnife;

/**
 * Base {@link android.app.Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {
    private final Collection<IPresenter> mPresenters = new ArrayList<>();

    public BaseFragment() {
        setRetainInstance(true);
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, fragmentView);
        initializeViews(savedInstanceState);
        return fragmentView;
    }

    protected abstract int getLayout();

    protected abstract void initializeViews(Bundle savedInstanceState);

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.injectDagger();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected abstract void injectDagger();

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override public void onResume() {
        super.onResume();
        for (IPresenter presenter : mPresenters) {
            if (presenter != null)
                presenter.onStart();
        }
    }

    public void registerPresenter(@NonNull IPresenter... presenters) {
        Collections.addAll(mPresenters, presenters);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        for (IPresenter presenter : mPresenters) {
            presenter.onDestroy();
        }
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked") protected <C> C getComponent(Class<C> componentType) {
        if (getActivity() != null) {
            return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
        }
        throw new NullPointerException();
    }
}
