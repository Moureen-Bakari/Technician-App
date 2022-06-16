package com.george.optica_technicianapplication.UI.Activities.Authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.george.optica_technicianapplication.R;
import com.george.optica_technicianapplication.UI.Activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Firebase Auth
    private FirebaseAuth firebaseAuth;
    private EditText emailInput, passwordInput;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        Button loginBtn = findViewById(R.id.loginBtn);
        emailInput = findViewById(R.id.emailInput);
        TextView noAccountTxt = findViewById(R.id.noAccountTxt);

        //initialize firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(this);
        noAccountTxt.setOnClickListener(this);
//        signupText.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                loginUser();
                break;
            case R.id.noAccountTxt:
                Intent intent = new Intent(this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    //call to loginUser Method
    private void loginUser() {
        String textEmail = emailInput.getText().toString().trim();
        String textPassword = passwordInput.getText().toString().trim();

        //check if the EditTexts are Empty
        if (TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)) {
            Toast.makeText(this, "Fill In All The Credentials", Toast.LENGTH_SHORT).show();
        } else {
            login(textEmail, textPassword);
        }
    }

    private void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Welcome To Optica", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finishAffinity();
    }
}
















