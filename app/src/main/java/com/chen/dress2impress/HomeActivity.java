package com.chen.dress2impress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.chen.dress2impress.model.Outfit;

public class HomeActivity extends AppCompatActivity implements OutfitsListFragment.Delegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void onItemSelected(Outfit outfit) {
        NavController navController = Navigation.findNavController(this, R.id.home_navigation);
        navController.navigate(R.id.action_outfitsListFragment_to_outfitDetailsFragment);
    }
}