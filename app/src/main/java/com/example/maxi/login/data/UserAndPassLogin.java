package com.example.maxi.login.data;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.maxi.login.activity.FragmentInteractorInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class UserAndPassLogin extends FirebaseLoginManager implements FirebaseLoginInterface {

    private String email;
    private String password;
    private Activity activity;
    FragmentInteractorInterface interactorInterface;

    public UserAndPassLogin(FragmentInteractorInterface interactorInterface, String email, String password) {
        super();
        this.email = email;
        this.password = password;
        this.activity = interactorInterface.getActivityInstance();
        this.interactorInterface = interactorInterface;
    }

    @Override
    public void signIn() {
        interactorInterface.hideButton();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(activity, email + " not loged", Toast.LENGTH_SHORT).show();
                            interactorInterface.showButton();
                        } else {
                            startMainActivity(activity);
                        }
                    }
                });
    }

    @Override
    public void signUp() {
        interactorInterface.hideButton();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:notComplete:");
                            Toast.makeText(activity, email + " not created", Toast.LENGTH_SHORT).show();
                            interactorInterface.showButton();
                        } else {
                            startMainActivity(activity);
                        }
                    }
                });
    }
}
