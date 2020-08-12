package com.chen.dress2impress;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.user.UserModel;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OutfitsListFragment.Delegate {
    NavController navController;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navController = Navigation.findNavController(this, R.id.main_nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.outfitsListFragment:
                        navController.navigateUp();
                        navController.navigate(R.id.outfitsListFragment);
                        break;
                    case R.id.newOutfitFragment:
                        if (UserModel.instance.isUserLoggedIn()) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("Outfit", new Outfit());
                            navController.navigate(R.id.newOutfitFragment, bundle);
                        } else {
                            navController.navigateUp();
                            navController.navigate(R.id.action_global_loginFragment);
                        }
                        break;
                    case R.id.userProfileFragment:
                        if (UserModel.instance.isUserLoggedIn())
                            navController.navigate(R.id.userProfileFragment);
                        else {
                            navController.navigateUp();
                            navController.navigate(R.id.action_global_loginFragment);
                        }
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

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
