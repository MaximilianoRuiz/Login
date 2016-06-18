package com.example.maxi.login.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.maxi.login.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseLoginManager {

    public static String TAG = "FirebaseLoginManager";

    protected FirebaseAuth auth;
    protected FirebaseAuth.AuthStateListener mAuthListener;


    public FirebaseLoginManager() {
        auth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseAuth.AuthStateListener getmAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        return mAuthListener;
    }

    protected void startMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
}
