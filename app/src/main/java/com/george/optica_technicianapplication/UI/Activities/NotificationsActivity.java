package com.george.optica_technicianapplication.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.george.optica_technicianapplication.UI.Activities.SplashScreen.SplashScreen;
import com.george.optica_technicianapplication.databinding.ActivityNotificationsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class NotificationsActivity extends AppCompatActivity {

    private ActivityNotificationsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(NotificationsActivity.this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    /*BAck Button Functionality*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}