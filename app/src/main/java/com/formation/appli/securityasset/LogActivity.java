package com.formation.appli.securityasset;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

    private TextView tv_log_Status;
    private TextView tv_log_Detail;
    private EditText et_log_mailField;
    private EditText et_log_PasswordField;

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
        tv_log_Status = (TextView) findViewById(R.id.status);
        tv_log_Detail = (TextView) findViewById(R.id.detail);
        et_log_mailField = (EditText) findViewById(R.id.field_email);
        et_log_PasswordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_create_account_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.go_control_activity).setOnClickListener(this);
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

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(LogActivity.this, R.string.user_creation_succeded, Toast.LENGTH_SHORT).show();
                            sendEmailVerification();
                            //Toast.makeText(LogActivity.this, "Go to your mail to validate your account", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LogActivity.this, R.string.user_creation_failed,
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

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LogActivity.this, R.string.user_authentication_succeded,
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogActivity.this, R.string.user_authentication_failed,
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            tv_log_Status.setText(R.string.user_authentication_failed);
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void goToControlActivity() {
        Intent intent = new Intent(LogActivity.this, ControlActivity.class);
        startActivity(intent);
        //finish();
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.go_control_activity).setEnabled(false);
        //String messageToToast="";
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button


                        if (task.isSuccessful()) {

                            Toast.makeText(LogActivity.this,
                                    getString(R.string.verification_email_sent) + user.getEmail()+"\n please validate your account",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LogActivity.this,
                                    R.string.failed_verification_email_sending,
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_log_mailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_log_mailField.setError(getText(R.string.required_field));
            valid = false;
        } else {
            et_log_mailField.setError(null);
        }

        String password = et_log_PasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_log_PasswordField.setError(getText(R.string.required_field));
            valid = false;
        } else {
            et_log_PasswordField.setError(null);
        }

        return valid;
    }

    private void hideProgressDialog() {
    }

    private void showProgressDialog() {

    }


    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            tv_log_Status.setText(getString(R.string.emailpassword_status_fmt,
                    currentUser.getEmail(), currentUser.isEmailVerified()));
            tv_log_Detail.setText(getString(R.string.firebase_status_fmt, currentUser.getUid()));

            findViewById(R.id.email_password_buttons).setVisibility(View.GONE);
            findViewById(R.id.email_password_fields).setVisibility(View.GONE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.VISIBLE);

            findViewById(R.id.go_control_activity).setEnabled(currentUser.isEmailVerified());

        } else {
            tv_log_Status.setText(R.string.signed_out);
            tv_log_Detail.setText(null);

            findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
            findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
            findViewById(R.id.signed_in_buttons).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_create_account_button:
                String mail = et_log_mailField.getText().toString();
                String pwd = et_log_PasswordField.getText().toString();
                createAccount(et_log_mailField.getText().toString(), et_log_PasswordField.getText().toString());
                break;
            case R.id.email_sign_in_button:
                signIn(et_log_mailField.getText().toString(), et_log_PasswordField.getText().toString());
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            case R.id.go_control_activity:
                goToControlActivity();
                break;
        }

    }


}
