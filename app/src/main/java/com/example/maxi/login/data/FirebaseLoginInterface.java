package com.example.maxi.login.data;

import android.content.Intent;

public interface FirebaseLoginInterface {

    void signIn();
    void signUp();
    void activityResult(int requestCode, int resultCode, Intent data);
}
