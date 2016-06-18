package com.example.maxi.login.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.maxi.login.R;
import com.example.maxi.login.activity.FragmentInteractorInterface;
import com.example.maxi.login.data.FacebookLogin;
import com.example.maxi.login.data.GmailLogin;
import com.example.maxi.login.data.UserAndPassLogin;

public class SignInFragment extends Fragment implements FragmentInteractorInterface{

    private Button btnEmailAndPassLogin;
    private Button btnFacebookLogin;
    private Button btGmailLogin;
    private EditText etEmailLogin;
    private EditText etPasswordLogin;
    private ProgressBar pbEmailAndPassLogin;

    private FragmentInteractorInterface iInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sing_in_fragment, container, false);

        iInteractor = this;

        initWidgets(view);

        addListeners();

        return view;
    }

    private void initWidgets(View view) {
        btnEmailAndPassLogin = (Button) view.findViewById(R.id.btnEmailAndPassLogin);
        btnFacebookLogin = (Button) view.findViewById(R.id.btnFacebookLogin);
        btGmailLogin = (Button) view.findViewById(R.id.btGmailLogin);

        etEmailLogin = (EditText) view.findViewById(R.id.etEmailLogin);
        etPasswordLogin = (EditText) view.findViewById(R.id.etPasswordLogin);

        pbEmailAndPassLogin = (ProgressBar) view.findViewById(R.id.pbEmailAndPassLogin);
    }

    private void addListeners() {
        btnEmailAndPassLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmailLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();
                if(!"".equals(email) && !"".equals(password)) {
                    UserAndPassLogin login = new UserAndPassLogin(iInteractor, email, password);
                    login.signIn();
                } else {
                    Toast.makeText(getActivity(), "Empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFacebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FacebookLogin login = new FacebookLogin(getActivity());
                login.signIn();
            }
        });

        btGmailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GmailLogin login = new GmailLogin(getActivity());
                login.signIn();
            }
        });
    }

    @Override
    public Activity getActivityInstance() {
        return getActivity();
    }

    @Override
    public void hideButton() {
        pbEmailAndPassLogin.setVisibility(View.VISIBLE);
        btnEmailAndPassLogin.setVisibility(View.GONE);
    }

    @Override
    public void showButton() {
        pbEmailAndPassLogin.setVisibility(View.GONE);
        btnEmailAndPassLogin.setVisibility(View.VISIBLE);
    }
}
