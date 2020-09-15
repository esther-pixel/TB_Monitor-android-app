package com.softmekdev.client15;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.softmekdev.client15.activities.Questionaire;

public class MainActivity extends AppCompatActivity {


    /*Reference == https://www.androidtutorialpoint.com/firebase/real-time-android-chat-application-using-firebase-tutorial/*/
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth firebaseAuth;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";

    private Button btnSignOut, btnlogin, question;
    private int RC_SIGN_IN = 1;
    private TextView acccount_create;
    private EditText email, password;

    /*Github url
     * git clone https://haronsoft@bitbucket.org/haronsoft/tb_app.git
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.sign_in_button);
        firebaseAuth = FirebaseAuth.getInstance();
        btnSignOut = findViewById(R.id.sign_out_button);
        acccount_create = findViewById(R.id.create_account);
        btnlogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUserExist();
            }
        });
        acccount_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut();
                Toast.makeText(MainActivity.this, "You are Logged Out", Toast.LENGTH_SHORT).show();
                btnSignOut.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void checkUserExist() {
        String mail = email.getText().toString().trim();
        if (mail.isEmpty()) {
            Toast.makeText(this, "Email field Required", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.fetchProvidersForEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                            boolean check = task.getResult().getProviders().isEmpty();
                            if (check) {
                                Toast.makeText(MainActivity.this, "Email Not found!", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String servermail = user.getEmail();
                                Toast.makeText(MainActivity.this, "User :" + servermail, Toast.LENGTH_SHORT).show();
                                String mail = email.getText().toString().trim();
                                String pass = password.getText().toString().trim();
                                firebaseAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Intent questionaire_intent = new Intent(MainActivity.this, Questionaire.class);
                                            startActivity(questionaire_intent);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Error ", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });

    /*    authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseAuth != null) {
                    Toast.makeText(MainActivity.this, "You are already Logged in", Toast.LENGTH_SHORT).show();
                } else {
                    Intent main = new Intent(MainActivity.this, CreateAccount.class);
                    startActivity(main);
                }
            }
        };*/
        }
    }

    private void createAccount() {
        //Create account and save data to firebase
        Intent createAccount = new Intent(MainActivity.this, com.softmekdev.tbmonitor.activities.CreateAccount.class);
        startActivity(createAccount);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(MainActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {
            Toast.makeText(MainActivity.this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        //check if the account is null
        if (acct != null) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        } else {
            Toast.makeText(MainActivity.this, "acc failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser fUser) {
        btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            /*Launch Questionaire*/
            Intent questionaire_intent = new Intent(MainActivity.this, Questionaire.class);
            startActivity(questionaire_intent);
            Toast.makeText(MainActivity.this, personName + personEmail, Toast.LENGTH_SHORT).show();
        }

    }


}
