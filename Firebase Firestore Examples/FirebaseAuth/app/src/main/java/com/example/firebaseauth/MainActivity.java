 package com.example.firebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


 public class MainActivity extends AppCompatActivity implements View.OnClickListener {

     private EditText editTextEmail, editTextPassword;
     private Button loginButton;
     private ProgressBar progressBar;
     private TextView signUpText;
     private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        signUpText = findViewById(R.id.textViewSignup);
        progressBar = findViewById(R.id.progressbar);

        signUpText.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }


     private void userLogin() {
         String email = editTextEmail.getText().toString().trim();
         String password = editTextPassword.getText().toString().trim();

         if(email.isEmpty()){
             editTextEmail.setError("Email is Required");
             editTextEmail.requestFocus();
             return;
         }

         if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             editTextEmail.setError("Please enter a valid email");
             editTextEmail.requestFocus();
             return;
         }

         if(password.isEmpty()){
             editTextPassword.setError("Password is required");
             editTextPassword.requestFocus();
             return;
         }

         if(password.length() < 6){
             editTextPassword.setError("Minimum length of password should be 6");
             editTextPassword.requestFocus();
             return;
         }
         progressBar.setVisibility(View.VISIBLE);

         mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 progressBar.setVisibility(View.GONE);
                 if(task.isSuccessful()){
                     finish();
                     Intent intent = new Intent(MainActivity.this,Profile.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     startActivity(intent);
                 }
                 else{
                     Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                 }
             }
         });
     }

     @Override
     protected void onStart() {
         super.onStart();
         if(mAuth.getCurrentUser() != null){
             finish();
             startActivity(new Intent(this,Profile.class));
         }
     }

     @Override
     public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.buttonLogin:
                userLogin();
                break;
        }
     }
 }
