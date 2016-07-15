package com.example.maxi.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.maxi.login.R;
import com.example.maxi.login.data.FirebaseLoginManager;
import com.facebook.login.LoginManager;

public class MainActivity extends AppCompatActivity {

    private TextView tvUserFirebaseEmail, tvUserFirebaseID;
    private Button btnLogOut;

    private Bundle bundle;

    private String userFirebaseEmail;
    private String userFirebaseID;

    private FirebaseLoginManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseManager = new FirebaseLoginManager();

        bundle = getIntent().getExtras();
        userFirebaseEmail = bundle.getString(FirebaseLoginManager.USER_FIREBASE_EMAIL);
        userFirebaseID = bundle.getString(FirebaseLoginManager.USER_FIREBASE_ID);

        initWidgets();

        addListeners();
    }

    private void initWidgets() {
        tvUserFirebaseEmail = (TextView) findViewById(R.id.tvUserFirebaseEmail);
        tvUserFirebaseID = (TextView) findViewById(R.id.tvUserFirebaseID);
        btnLogOut = (Button) findViewById(R.id.btnLogOut);

        tvUserFirebaseEmail.setText(userFirebaseEmail);
        tvUserFirebaseID.setText(userFirebaseID);
    }

    private void addListeners() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseManager.getAuth().signOut();
                LoginManager.getInstance().logOut();
                finish();
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
