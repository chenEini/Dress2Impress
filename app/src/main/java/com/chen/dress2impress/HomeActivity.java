package com.chen.dress2impress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.chen.dress2impress.model.Outfit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements OutfitsListFragment.Delegate {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navController = Navigation.findNavController(this, R.id.home_navigation_graph);
        NavigationUI.setupActionBarWithNavController(this, navController);

        BottomNavigationView bottomNavigation = findViewById(R.id.home_bottom_navigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }

    @Override
    public void onItemSelected(Outfit outfit) {
        navController = Navigation.findNavController(this, R.id.home_navigation_graph);

        NavGraphDirections.ActionGlobalOutfitDetailsFragment direction = OutfitsListFragmentDirections.actionGlobalOutfitDetailsFragment(outfit);
        navController.navigate(direction);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navController.navigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}