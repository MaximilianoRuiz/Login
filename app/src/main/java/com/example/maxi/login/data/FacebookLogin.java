package com.example.maxi.login.data;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.maxi.login.R;
import com.example.maxi.login.fragment.FragmentInteractorInterface;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

public class FacebookLogin extends FirebaseLoginManager implements FirebaseLoginInterface {

    private LoginButton loginButton;
    private Activity activity;
    private FragmentInteractorInterface interactorInterface;
    private CallbackManager mCallbackManager;
    private Resources resources;

    public FacebookLogin(FragmentInteractorInterface interactorInterface) {
        this.activity = interactorInterface.getActivityInstance();
        this.activity = interactorInterface.getActivityInstance();
        this.interactorInterface = interactorInterface;
        this.resources = activity.getResources();

        init();
    }

    private void init() {
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = interactorInterface.getLoginButton();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        getAuth().signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        startMainActivity(activity);
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            dismissDialog();
                            Toast.makeText(activity, resources.getText(R.string.authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void signIn() {
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                dismissDialog();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                dismissDialog();
            }
        });
    }

    @Override
    public void signUp() {}

    @Override
    public void activityResult(int requestCode, int resultCode, Intent data) {
        showDialog(activity);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
