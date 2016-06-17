package com.example.maxi.login.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maxi.login.R;
import com.example.maxi.login.data.FacebookLogin;
import com.example.maxi.login.data.FirebaseLoginManager;
import com.example.maxi.login.data.GmailLogin;
import com.example.maxi.login.data.UserAndPassLogin;

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
                    .add(R.id.container, new SingInFragment())
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
                .replace(R.id.container, new SingUpFragment())
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

    public static class SingInFragment extends Fragment {

        private Button btnEmailAndPassLogin;
        private Button btnFacebookLogin;
        private Button btGmailLogin;
        private EditText etEmailLogin;
        private EditText etPasswordLogin;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.sing_in_fragment, container, false);

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
        }

        private void addListeners() {
            btnEmailAndPassLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = etEmailLogin.getText().toString();
                    String password = etPasswordLogin.getText().toString();
                    if(!"".equals(email) && !"".equals(password)) {
                        UserAndPassLogin login = new UserAndPassLogin(getActivity(), email, password);
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
    }

    public static class SingUpFragment extends Fragment {

        private EditText etEmailSignUp;
        private EditText etPasswordSignUp;
        private EditText etPasswordSignUpRepeat;
        private Button btnSignUp;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.sing_up_fragment, container, false);

            initWidgets(view);

            addListeners();

            return view;
        }

        private void initWidgets(View view) {
            etEmailSignUp = (EditText) view.findViewById(R.id.etEmailSignUp);
            etPasswordSignUp = (EditText) view.findViewById(R.id.etPasswordSignUp);
            etPasswordSignUpRepeat = (EditText) view.findViewById(R.id.etPasswordSignUpRepeat);

            btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        }

        private void addListeners() {
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = etEmailSignUp.getText().toString();
                    String password = etPasswordSignUp.getText().toString();
                    if(!"".equals(email) && !"".equals(password)) {
                        UserAndPassLogin login = new UserAndPassLogin(getActivity(), email, password);
                        login.signUp();
                        etEmailSignUp.setText("");
                        etPasswordSignUp.setText("");
                        etPasswordSignUpRepeat.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Empty fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
