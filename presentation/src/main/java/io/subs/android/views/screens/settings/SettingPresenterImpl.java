package io.subs.android.views.screens.settings;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.BuildConfig;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.domain.usecases.session.SignOut;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class SettingPresenterImpl extends BaseRxPresenter implements SettingPresenter {
    private final Context context;
    private final SignOut signOut;
    private final SettingFlowListener settingFlowListener;

    @Inject public SettingPresenterImpl(Context context, SignOut signOut,
            SettingFlowListener settingFlowListener) {
        this.context = context;
        this.signOut = signOut;
        this.settingFlowListener = settingFlowListener;
    }

    @Override public void signOutUser() {
        manage(signOut.execute(new DisposableObserver<Void>() {
            @Override public void onNext(@NonNull Void aVoid) {
            }

            @Override public void onError(@NonNull Throwable e) {

            }

            @Override public void onComplete() {
                settingFlowListener.showLoginScreen();
            }
        }, null));
    }

    @Override public void shareApplication() {
        String shareBody = "Check out \"subs\" an awesome way to track subscriptions"
                + "\n\n"
                + "http://play.google.com/store/apps/details?id="
                + context.getPackageName();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SUBS");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        settingFlowListener.startActivity(Intent.createChooser(sharingIntent, "Choose One"));
    }

    @Override public void sendFeedback() {
        String body = "\n\n\n\n\n"
                + "Version Code: "
                + BuildConfig.VERSION_CODE
                + '\n'
                + "Version Name: "
                + BuildConfig.VERSION_NAME
                + '\n'
                + "OS API Level: "
                + Build.VERSION.SDK_INT
                + '\n'
                + "Device: "
                + Build.MODEL
                + '\n'
                + '}';
        //body.append("OS Version: ").append(BuildConfig.VERSION_CODE).append('\n');
        //body.append("Model (and Product): ").append(Build.MANUFACTURER).append('\n');
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "riteshakya037@gmail.com" });
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Feedback");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        settingFlowListener.startActivity(Intent.createChooser(sharingIntent, "Choose One"));
    }

    @Override public void rateApplication() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY
                    | Intent.FLAG_ACTIVITY_NEW_DOCUMENT
                    | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        } else {
            goToMarket.addFlags(
                    Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        }
        try {
            settingFlowListener.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            settingFlowListener.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(
                    "http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }
}
