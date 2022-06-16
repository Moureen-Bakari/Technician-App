package com.george.optica_technicianapplication.UI.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.george.optica_technicianapplication.R;
import com.george.optica_technicianapplication.UI.Fragments.AccountFragment;
import com.george.optica_technicianapplication.UI.Fragments.HomeFragment;
import com.george.optica_technicianapplication.UI.Fragments.OrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //set Toolbar as the main app bar
        setSupportActionBar(toolbar);

        bottomNavigationView.setOnItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment());
        transaction.commit();
    }
    /*Removes the scroll bars*/
    private void disableNavigationViewScrollBars(NavigationView navigationView) {
        //check if the navigation View is not null
        if (navigationView != null) {
            //get a reference to the navigation MenuView and get the index of the starting menu
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            //check if it has value
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }


    //OnNavigationView Item Selected
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //create an instance of Fragment
        Fragment fragment = null;
        //Intent instance
        Intent intent = null;

        switch (item.getItemId()) {
            case R.id.home:
                fragment = new HomeFragment();
                break;

            case R.id.batch:
                fragment = new OrdersFragment();
                break;

            case R.id.account:
                fragment = new AccountFragment();
                break;
        }
        //check if fragment is not null
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        } else if (intent != null) {
            startActivity(intent);
        }

        return true;
    }

}















