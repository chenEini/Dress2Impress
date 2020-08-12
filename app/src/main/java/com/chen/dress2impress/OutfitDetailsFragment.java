package com.chen.dress2impress;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.outfit.OutfitModel;
import com.chen.dress2impress.model.user.UserModel;

import java.util.LinkedList;
import java.util.List;

public class OutfitDetailsFragment extends Fragment {
    private Outfit outfit;
    View view;

    TextView outfitTitle;
    TextView outfitDescription;

    public OutfitDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_outfit_details, container, false);

        outfitTitle = view.findViewById(R.id.outfit_details_title);
        outfitDescription = view.findViewById(R.id.outfit_details_description);

        outfit = OutfitDetailsFragmentArgs.fromBundle(getArguments()).getOutfit();
        if (outfit != null) updateOutfitDisplay();

        View backButton = view.findViewById(R.id.outfit_details_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (true || outfit.ownerId == UserModel.instance.getCurrentUser().id) {
            inflater.inflate(R.menu.outfit_details_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(view);

        switch (item.getItemId()) {
            case R.id.menu_outfit_details_update:
                NavDirections updatedDirections = OutfitDetailsFragmentDirections.actionGlobalNewOutfitFragment();
                navController.navigate(updatedDirections);
                return true;
            case R.id.menu_outfit_details_delete:
                List<Outfit> outfitToDelete = new LinkedList<>();
                outfitToDelete.add(outfit);
                //OutfitModel.instance.deleteOutfits(outfitToDelete);
                navController.navigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateOutfitDisplay() {
        outfitTitle.setText(outfit.title);
        outfitDescription.setText(outfit.description);
    }
}