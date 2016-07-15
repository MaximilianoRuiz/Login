package com.example.maxi.login.data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.maxi.login.R;
import com.example.maxi.login.activity.MainActivity;
import com.example.maxi.login.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseLoginManager implements FirebaseLoginInterface {

    public static String TAG = "FirebaseLoginManager";
    public static final String USER_FIREBASE_ID = "userFirebaseID";
    public static final String USER_FIREBASE_EMAIL = "userFirebaseEmail";

    protected FirebaseAuth auth;
    private FirebaseDatabase database;
    protected FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog;

    public FirebaseLoginManager() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
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
        intent.putExtra(USER_FIREBASE_EMAIL, auth.getCurrentUser().getEmail());
        intent.putExtra(USER_FIREBASE_ID, auth.getCurrentUser().getUid());
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    public void signIn() {
    }

    @Override
    public void signUp() {
    }

    @Override
    public void activityResult(int requestCode, int resultCode, Intent data) {
    }

    protected void showDialog(Activity activity) {
        progressDialog = ProgressDialog.show(activity,
                activity.getResources().getText(R.string.progress_dialog_title),
                activity.getResources().getText(R.string.progress_dialog_message),
                true);
        progressDialog.setCancelable(true);
    }

    protected void dismissDialog() {
       // progressDialog.dismiss();
    }

    protected void registerUser() {
        final String uid = auth.getCurrentUser().getUid();
        final DatabaseReference myRef = database.getReference("users/" + uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) {
                    user = new User();
                    user.setMail(auth.getCurrentUser().getEmail());
                    myRef.setValue(user);
                } else {
                    Log.d(TAG, "Value is: " + user);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }
}
