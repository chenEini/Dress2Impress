package com.chen.dress2impress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.chen.dress2impress.model.Outfit;

public class HomeActivityFragment extends AppCompatActivity implements OutfitsListFragment.Delegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);

        OutfitsListFragment outfitsListFragment = new OutfitsListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container, outfitsListFragment, "TAG");
        transaction.commit();
    }

    void openOutfitDetails() {
        OutfitDetailsFragment outfitDetailsFragment = new OutfitDetailsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container, outfitDetailsFragment, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }

    @Override
    public void onItemSelected(Outfit outfit) {
        openOutfitDetails();
    }
}