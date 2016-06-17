package com.example.maxi.login.data;

import android.app.Activity;
import android.widget.Toast;

public class FacebookLogin extends FirebaseLoginManager implements FirebaseLoginInterface {

    private Activity activity;

    public FacebookLogin(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void signIn() {
        Toast.makeText(activity, "Working on it", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void signUp() {
        Toast.makeText(activity, "Working on it", Toast.LENGTH_SHORT).show();
    }
}
