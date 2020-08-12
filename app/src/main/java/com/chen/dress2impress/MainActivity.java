package com.chen.dress2impress;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.chen.dress2impress.model.outfit.Outfit;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OutfitsListFragment.Delegate {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                getSupportActionBar().setTitle(destination.getLabel());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onItemSelected(String source, Outfit outfit) {
        switch (source) {
            case "fragment_outfits_list":
                OutfitsListFragmentDirections.ActionOutfitsListFragmentToOutfitDetailsFragment listDirection = OutfitsListFragmentDirections.actionOutfitsListFragmentToOutfitDetailsFragment(outfit);
                navController.navigate(listDirection);
                break;
            case "fragment_user_profile":
                UserProfileFragmentDirections.ActionUserProfileFragmentToOutfitDetailsFragment profileDirection = UserProfileFragmentDirections.actionUserProfileFragmentToOutfitDetailsFragment(outfit);
                navController.navigate(profileDirection);
                break;
        }
    }
}
