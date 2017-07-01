package com.formation.appli.securityasset;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogActivity extends AppCompatActivity implements
        View.OnClickListener {

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "EmailPassword";

    private TextView tv_main_Status;
    private TextView tv_main_Detail;
    private EditText et_main_mailField;
    private EditText et_main_PasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initView();

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void initView() {
        // Views
        tv_main_Status = (TextView) findViewById(R.id.status);
        tv_main_Detail = (TextView) findViewById(R.id.detail);
        et_main_mailField = (EditText) findViewById(R.id.field_email);
        et_main_PasswordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.verify_email_button).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(final String email, final String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task = FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password);
                        boolean test=task.isSuccessful();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LogActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_main_mailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_main_mailField.setError("Required.");
            valid = false;
        } else {
            et_main_mailField.setError(null);
        }

        String password = et_main_PasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_main_PasswordField.setError("Required.");
            valid = false;
        } else {
            et_main_PasswordField.setError(null);
        }

        return valid;
    }

    private void hideProgressDialog() {
    }

    private void showProgressDialog() {

    }


    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            tv_main_Status.setText(getString(R.string.emailpassword_status_fmt,
                    currentUser.getEmail(), currentUser.isEmailVerified()));
            tv_main_Detail.setText(getString(R.string.firebase_status_fmt, currentUser.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.verify_email_button).setEnabled(!currentUser.isEmailVerified());
        } else {
            tv_main_Status.setText(R.string.signed_out);
            tv_main_Detail.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_create_account_button:
                String mail=et_main_mailField.getText().toString();
                String pwd =  et_main_PasswordField.getText().toString();
                createAccount(et_main_mailField.getText().toString(), et_main_PasswordField.getText().toString());
                break;
            case R.id.email_sign_in_button:
                //signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
                break;
            case R.id.sign_out_button:
                //signOut();
                break;
            case R.id.verify_email_button:
                //sendEmailVerification();
                break;
        }

    }
}
