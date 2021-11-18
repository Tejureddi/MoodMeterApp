package com.example.moodmeterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //private Button signInButton;
    private FirebaseAuth mAuth;

    EditText emailET, passwordET;
    TextView authStatusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //signInButton = (Button) findViewById(R.id.signInButton);
        //signInButton.setOnClickListener(this::handleAuthChange);

        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        mAuth = FirebaseAuth.getInstance();

    }

    public void directionsClick(View view) {
        Intent intent = new Intent(MainActivity.this, DirectionsPage.class);
        startActivity(intent);


    }



    //each time this screen is started we will know if the user is signed in
    // (meaning the mAuth var is not null) and then we can update the screen accordingly.
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //user.getUid() --> to save user data
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }



   /*
   This method will get all user input and then based on the button chose, perform the
   desired action.

   https://stackoverflow.com/questions/13032333/droid-how-to-get-button-id-from-onclick-method-described-in-xml
    */

    public void handleAuthChange(View v) {
        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        Log.i("Denna",  email + " " + password);

        switch (v.getId()) {
            case R.id.signInButton:
                signIn(email, password);
                //
                break;
            case R.id.signUpButton:
                //
                signUp(email, password);
                break;
        }
    }

    public void signUp(String email, String password) {
        //authStatusTV.setText("Signed up");
        // If the email and password passed in are not null, then try to create a User
        if (email != null && password != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("Denna", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(MainActivity.this, MoodPickerActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.i("Denna", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void signIn(String email, String password) {
        //authStatusTV.setText("Signed in");
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("Denna", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent1 = new Intent(MainActivity.this, MoodPickerActivity.class);
                            startActivity(intent1);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("Denna", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }



}  