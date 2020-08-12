package com.chen.dress2impress;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.user.User;
import com.squareup.picasso.Picasso;

public class OutfitDetailsFragment extends Fragment {
    private OutfitDetailsViewModel viewModel;
    private Outfit outfit;
    View view;

    TextView outfitTitle;
    TextView outfitDescription;
    ImageView outfitImage;

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
        outfitImage = view.findViewById(R.id.outfit_details_image);

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

        viewModel = new ViewModelProvider(this).get(OutfitDetailsViewModel.class);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        User user = viewModel.getCurrentUser();
        if (user != null && outfit.ownerId.equals(user.id)) {
            inflater.inflate(R.menu.outfit_details_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(view);

        switch (item.getItemId()) {
            case R.id.menu_outfit_details_update:
                NavDirections updatedDirections = OutfitDetailsFragmentDirections.actionGlobalNewOutfitFragment(outfit);
                navController.navigate(updatedDirections);
                return true;
            case R.id.menu_outfit_details_delete:
                viewModel.deleteOutfit(outfit);
                navController.navigateUp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateOutfitDisplay() {
        outfitTitle.setText(outfit.title);
        outfitDescription.setText(outfit.description);

        if (outfit.imageUrl != null && !outfit.imageUrl.isEmpty())
            Picasso.get().load(outfit.imageUrl).placeholder(R.drawable.outfit).into(outfitImage);
        else
            outfitImage.setImageResource(R.drawable.outfit);
    }
}