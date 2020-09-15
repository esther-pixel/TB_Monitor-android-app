package com.softmekdev.tbmonitor.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.softmekdev.client15.R;

import java.util.HashMap;

public class CreateAccount extends AppCompatActivity {

    /*Views*/
    Button btn_submit;
    EditText firstname, lastname, email, location, contact, username, password, confirm_password;

    /*Firebase Database*/
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    DatabaseReference myRefChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        this.setTitle("Account Creation");

        /*Initialization*/
        firebaseAuth = FirebaseAuth.getInstance();
        btn_submit = findViewById(R.id.btn_create_account);

        lastname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        location = findViewById(R.id.location);
        contact = findViewById(R.id.contact);
        username = findViewById(R.id.username);
        firstname = findViewById(R.id.fname);
        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Correct all data and submit them to firebase database
                submitData();
            }
        });
    }

    private void submitData() {
        Toast.makeText(this, firstname.getText().toString(), Toast.LENGTH_SHORT).show();
        // Write a message to the database
        /*Get data from the form and convert them to String*/
        String firt_name = firstname.getText().toString().trim();
        String last_name = lastname.getText().toString().trim();
        String mail = email.getText().toString().trim();
        String loca_tion = location.getText().toString().trim();
        String con_tact = contact.getText().toString().trim();
        String pass_word = password.getText().toString().trim();
        String confirm_pass = confirm_password.getText().toString().trim();

        /*Send user data in Hashmap Format*/
        HashMap<String, Object> userdata_map = new HashMap<>();
        userdata_map.put("firstname", firt_name);
        userdata_map.put("lastname", last_name);
        userdata_map.put("email", mail);
        userdata_map.put("location", loca_tion);
        userdata_map.put("contact", con_tact);
        userdata_map.put("password", pass_word);
        userdata_map.put("confirmpassword", confirm_pass);

        // Access a Cloud Firestore instance from your Activity
        // FirebaseFirestore db = FirebaseFirestore.getInstance();
        //db.collection()


   /*     myRefChild = database.getReference("users")
                .child(mail);

        myRef.setValue(userdata_map);
        myRefChild.push().setValue(userdata_map);
        if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass_word)) {
            Toast.makeText(CreateAccount.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
        } else if (pass_word.length() < 6) {
            Toast.makeText(CreateAccount.this, "Password too short!", Toast.LENGTH_SHORT).show();
        } else {
            checkUserExist();
            registerUser(mail, pass_word);
        }*/
    }

    private void checkUserExist() {


    }

    private void registerUser(String mail, String pass_word) {
        firebaseAuth.createUserWithEmailAndPassword(mail, pass_word).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccount.this, "Registering user successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccount.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
