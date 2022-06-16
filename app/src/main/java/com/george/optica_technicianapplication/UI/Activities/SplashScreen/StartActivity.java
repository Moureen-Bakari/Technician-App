package com.george.optica_technicianapplication.UI.Activities.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.george.optica_technicianapplication.R;
import com.george.optica_technicianapplication.UI.Activities.Authentication.LoginActivity;
import com.george.optica_technicianapplication.UI.Activities.Authentication.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout linearLayout;
    private ImageView iconLogo;
    private Button login, register;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //Initialize
        linearLayout = findViewById(R.id.linearLayout);
        iconLogo = findViewById(R.id.iconLogo);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);


        linearLayout.animate().alpha(0f).setDuration(10);


        TranslateAnimation animation = new TranslateAnimation(0,0,0,0);
        animation.setDuration(3000);
        animation.setFillAfter(false);
        animation.setAnimationListener(new MyAnimationListener());

        iconLogo.setAnimation(animation);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.register:
                intent = new Intent(StartActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            iconLogo.clearAnimation();
            iconLogo.setVisibility(View.INVISIBLE);
            linearLayout.animate().alpha(1f).setDuration(1000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    //On Start method that checks if the user is already signed in or not
    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Intent intent = new Intent(StartActivity.this, SplashScreen.class);
            startActivity(intent);
            finish();
        }
    }
}