package io.subs.android.views.screens.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import io.reactivex.observers.DisposableObserver;
import io.subs.android.R;
import io.subs.android.mvp.BaseRxPresenter;
import io.subs.domain.usecases.session.GetLoginStatus;
import javax.inject.Inject;

/**
 * @author Ritesh Shakya
 */

public class LoginPresenterImpl extends BaseRxPresenter implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";
    private Context context;
    private GetLoginStatus getLoginStatus;
    private LoginFlowListener loginFlowListener;
    private LoginView loginView;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Inject public LoginPresenterImpl(Context context, GetLoginStatus getLoginStatus,
            LoginFlowListener loginFlowListener) {
        this.context = context;
        this.getLoginStatus = getLoginStatus;
        this.loginFlowListener = loginFlowListener;
    }

    @Override public void setView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override public void initialize() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
                        context.getString(R.string.default_web_client_id)).requestEmail().build();
        mGoogleApiClient =
                new GoogleApiClient.Builder(context).enableAutoManage(loginView.getActivity(),
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override public void onConnectionFailed(
                                    @NonNull ConnectionResult connectionResult) {

                            }
                        } /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                initializeSession();
            }
        };
    }

    @Override public void validateResult(Intent data) {
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        if (result.isSuccess()) {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
        } else {
            Log.e(TAG, "onActivityResult: Error ");
            // Google Sign In failed, update UI appropriately
            // ...
        }
    }

    @Override public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(loginView.getActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG,
                                        "signInWithCredential:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithCredential", task.getException());
                                    Toast.makeText(context, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
    }

    @Override public void initializeSession() {
        manage(getLoginStatus.execute(new DisposableObserver<GetLoginStatus.LoginStatusType>() {
            @Override public void onNext(@io.reactivex.annotations.NonNull
                    GetLoginStatus.LoginStatusType loginStatusType) {
                if (loginStatusType == GetLoginStatus.LoginStatusType.ACTIVE) {
                    loginFlowListener.openMainActivity();
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }

            @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override public void onComplete() {

            }
        }, null));
    }

    @Override public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}

