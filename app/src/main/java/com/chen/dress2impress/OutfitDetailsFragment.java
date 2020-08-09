package com.chen.dress2impress;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.dress2impress.model.Outfit;

public class OutfitDetailsFragment extends Fragment {
    private Outfit outfit;

    TextView outfitTitle;
    TextView outfitDescription;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OutfitDetailsFragment() {
        // Required empty public constructor
    }

    public static OutfitDetailsFragment newInstance(String param1, String param2) {
        OutfitDetailsFragment fragment = new OutfitDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outfit_details, container, false);

        outfitTitle = view.findViewById(R.id.outfit_details_title);
        outfitDescription = view.findViewById(R.id.outfit_details_description);

        outfit = OutfitDetailsFragmentArgs.fromBundle(getArguments()).getOutfit();

        if (outfit != null) {
            updateOutfitDisplay();
        }

        View backBtn = view.findViewById(R.id.outfit_details_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });

        return view;
    }

    private void updateOutfitDisplay() {
        outfitTitle.setText(outfit.title);
        outfitDescription.setText(outfit.description);
    }
}