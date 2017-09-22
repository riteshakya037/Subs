package io.subs.android.views.screens.login;

import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 0;
    //private GoogleApiClient mGoogleApiClient;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    //
    //@OnClick(R.id.activity_login_google) void login() {
    //    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    //    startActivityForResult(signInIntent, RC_SIGN_IN);
    //}
    //
    //@Override protected void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //
    //    setContentView(R.layout.activity_login);
    //    ButterKnife.bind(this);
    //
    //    GoogleSignInOptions gso =
    //            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
    //                    getString(R.string.default_web_client_id)).requestEmail().build();
    //    mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,
    //            new GoogleApiClient.OnConnectionFailedListener() {
    //                @Override
    //                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    //
    //                }
    //            } /* OnConnectionFailedListener */).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    //    mAuth = FirebaseAuth.getInstance();
    //    mAuthListener = new FirebaseAuth.AuthStateListener() {
    //        @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
    //            FirebaseUser user = firebaseAuth.getCurrentUser();
    //            if (user != null) {
    //                startActivity(new Intent(LoginActivity.this, AddSubscriptionActivity.class));
    //                finish();
    //            } else {
    //                // User is signed out
    //                Log.d(TAG, "onAuthStateChanged:signed_out");
    //            }
    //        }
    //    };
    //}
    //
    //@Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);
    //    Log.i(TAG, "onActivityResult: ");
    //    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    //    if (requestCode == RC_SIGN_IN) {
    //        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
    //        if (result.isSuccess()) {
    //            // Google Sign In was successful, authenticate with Firebase
    //            GoogleSignInAccount account = result.getSignInAccount();
    //            firebaseAuthWithGoogle(account);
    //        } else {
    //            Log.e(TAG, "onActivityResult: Error ");
    //            // Google Sign In failed, update UI appropriately
    //            // ...
    //        }
    //    }
    //}
    //
    //private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
    //    Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
    //
    //    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    //    mAuth.signInWithCredential(credential)
    //            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    //                @Override public void onComplete(@NonNull Task<AuthResult> task) {
    //                    Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
    //
    //                    // If sign in fails, display a message to the user. If sign in succeeds
    //                    // the auth state listener will be notified and logic to handle the
    //                    // signed in user can be handled in the listener.
    //                    if (!task.isSuccessful()) {
    //                        Log.w(TAG, "signInWithCredential", task.getException());
    //                        Toast.makeText(LoginActivity.this, "Authentication failed.",
    //                                Toast.LENGTH_SHORT).show();
    //                    }
    //                }
    //            });
    //}
    //
    //@Override public void onStart() {
    //    super.onStart();
    //    mAuth.addAuthStateListener(mAuthListener);
    //}
    //
    //@Override public void onStop() {
    //    super.onStop();
    //    if (mAuthListener != null) {
    //        mAuth.removeAuthStateListener(mAuthListener);
    //    }
    //}
}