package com.example.taskapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity{
    boolean isTrue;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    ImageView imageView;
    TaskAdapter taskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.boardFragment, R.id.navigation_profile )
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//       boolean isShown = new Prefs(this).isShown();
//        if (!isShown)

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navController.navigate(R.id.phoneFragment);
        }






        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.navigation_dashboard ||destination.getId() == R.id.navigation_home){
                    navView.setVisibility(View.VISIBLE);
                } else {navView.setVisibility(View.GONE);}
                if (destination.getId() == R.id.navigation_dashboard ||destination.getId() == R.id.navigation_notifications
                        ||destination.getId() == R.id.navigation_home ||destination.getId() == R.id.formFragment ) {
                    getSupportActionBar().show();;
                } else {
                    getSupportActionBar().hide();;
                }
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,appBarConfiguration)  || super.onSupportNavigateUp();
    }

}