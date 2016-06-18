package com.example.maxi.login.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.CharacterPickerDialog;
import android.view.View;
import android.widget.Button;

import com.example.maxi.login.R;
import com.example.maxi.login.data.FirebaseLoginManager;

public class MainActivity extends AppCompatActivity {

    Button btnLogOut;

    private FirebaseLoginManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseManager = new FirebaseLoginManager();

        initWidgets();

        addListeners();
    }

    private void initWidgets() {
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
    }

    private void addListeners() {
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseManager.getAuth().signOut();
            }
        });
    }
}
