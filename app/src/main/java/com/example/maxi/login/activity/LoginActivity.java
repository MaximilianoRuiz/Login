package com.example.maxi.login.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.maxi.login.R;
import com.example.maxi.login.data.FirebaseLoginManager;
import com.example.maxi.login.fragment.SignInFragment;
import com.example.maxi.login.fragment.SignUpFragment;

public class LoginActivity extends AppCompatActivity {

    public static String TAG = "LoginActivity";
    private boolean mShowingBack = false;

    private Button btnSingIn;
    private Button btnSingUp;

    private FirebaseLoginManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, new SignInFragment())
                    .commit();
        }

        firebaseManager = new FirebaseLoginManager();

        initWidgets();

        addListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseManager.getAuth().addAuthStateListener(firebaseManager.getmAuthListener());
    }

    @Override
    public void onStop() {
        super.onStop();
        if (firebaseManager.getmAuthListener() != null) {
            firebaseManager.getAuth().removeAuthStateListener(firebaseManager.getmAuthListener());
        }
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            mShowingBack = false;
            return;
        }

        mShowingBack = true;

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
                .replace(R.id.container, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    private void initWidgets() {
        btnSingIn = (Button) findViewById(R.id.btnSingIn);
        btnSingUp = (Button) findViewById(R.id.btnSingUp);

        btnSingIn.setEnabled(false);
    }

    private void addListeners() {
        btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
                btnSingIn.setEnabled(false);
                btnSingUp.setEnabled(true);
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
                btnSingIn.setEnabled(true);
                btnSingUp.setEnabled(false);
            }
        });
    }
}
