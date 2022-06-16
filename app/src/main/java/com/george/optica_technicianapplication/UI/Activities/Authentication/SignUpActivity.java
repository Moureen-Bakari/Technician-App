package com.george.optica_technicianapplication.UI.Activities.Authentication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    //Firebase Database Reference
    DatabaseReference databaseReference;
    //Firebase Authentication
    FirebaseAuth firebaseAuth;
    //Progress Dialog Declaration
    ProgressDialog progressDialog;
    private EditText username, email, password, confirmPassword;
    private TextView textViewLogin;
//    private SpinKitView spinKitView;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //instantiating the views
        username = findViewById(R.id.usernameInput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPasswordInput);
        textViewLogin = findViewById(R.id.textViewLogin);
        register = findViewById(R.id.register);
//        spinKitView = findViewById(R.id.spinKit);

        //Instantiating the database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        //instantiating firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        //setting on click listener
        textViewLogin.setOnClickListener(this);
        register.setOnClickListener(this);

        //Progress Dialog Instatiation
        progressDialog = new ProgressDialog(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewLogin:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

            case R.id.register:
                registerUser();
                break;
        }
    }

    //register User into our application
    private void registerUser() {
/*
        Get the information from the edit text and convert it to string trimming any spaces.
        Confirm that none of the Edit Text are none
        Check for password Length
        call register Method passing the converted vales from the edit text as the arguments
*/
        String textUserName = username.getText().toString().trim();
        String textEmail = email.getText().toString().trim();
        String textPassword = password.getText().toString().trim();
        String textConfirmPassword = confirmPassword.getText().toString().trim();

        //confirm that none of the edittext are blank
        if (TextUtils.isEmpty(textUserName) || TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword) || TextUtils.isEmpty(textConfirmPassword)) {
            Toast.makeText(this, "Fill all the Credentials", Toast.LENGTH_SHORT).show();
        } else if (textPassword.length() < 8) {
            Toast.makeText(this, "Please create a password containing at least 6 characters", Toast.LENGTH_SHORT).show();
        } else if (!textPassword.equals(textConfirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            register(textUserName, textEmail, textPassword);
        }
    }

    /*
        Register Method that contains editText Inputs passed in as parameters
        Use the Firebase Auth object to Call the create user with Email and password and check if there is feed back or response
    */
    private void register(String username, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //display the progress Dialog and also display a message in the progress Dialog
                progressDialog.setMessage("Please wait");
                progressDialog.show();

                //Add values inside theHashMap and then create a new user by creating a node
                HashMap<String, Object> map = new HashMap<>();
                map.put("username", username);
                map.put("email", email);
                map.put("password", password);
                map.put("imageUrl", "default");

                //get the id of te user
                map.put("id", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                //Use the data base reference to add a new Node called User in case we don't find One
                databaseReference.child("Users").child(firebaseAuth.getCurrentUser().getUid()).child("UserLoginInfo").setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Login To Your Account", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                StyleableToast.makeText(SignUpActivity.this, e.getMessage(), R.style.styleToast).show();
            }
        });
    }
}



























