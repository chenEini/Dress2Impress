package com.chen.dress2impress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class HomeFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_fragment);

        OutfitsListFragment outfitsListFragment = (OutfitsListFragment) getSupportFragmentManager().findFragmentById(R.id.outfit_list_fragment);
        outfitsListFragment.setTitle("Hello From Chen");

        OutfitDetailsFragment outfitDetailsFragment = new OutfitDetailsFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.home_fragment_container, outfitDetailsFragment, "TAG");
        transaction.addToBackStack("TAG");
        transaction.commit();
    }
}