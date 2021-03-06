package com.chen.dress2impress;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.user.User;
import com.chen.dress2impress.model.user.UserModel;

import java.util.LinkedList;
import java.util.List;

public class UserProfileFragment extends Fragment {
    private UserProfileViewModel viewModel;

    View view;
    OutfitsListAdapter adapter;
    RecyclerView profileOutfitsList;
    List<Outfit> profileOutfitsData = new LinkedList<Outfit>();

    OutfitsListFragment.Delegate parent;

    public UserProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        User user = UserModel.instance.getCurrentUser();

        if (user != null) {
            TextView userName = view.findViewById(R.id.user_profile_user_name);
            userName.setText(user.name);
        }

        profileOutfitsList = view.findViewById(R.id.profile_outfits_list);
        profileOutfitsList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        profileOutfitsList.setLayoutManager(layoutManager);

        adapter = new OutfitsListAdapter();
        profileOutfitsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OutfitsListFragment.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Outfit outfit = profileOutfitsData.get(position);
                parent.onItemSelected("fragment_user_profile", outfit);
            }
        });

        LiveData<List<Outfit>> liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Outfit>>() {
            @Override
            public void onChanged(List<Outfit> outfits) {
                profileOutfitsData = outfits;
                adapter.notifyDataSetChanged();
            }
        });

        View logoutButton = view.findViewById(R.id.user_profile_logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                viewModel.logout();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.outfitsListFragment);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OutfitsListFragment.Delegate) {
            parent = (OutfitsListFragment.Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + "profile outfit list parent activity must implement delegate");
        }

        viewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    class OutfitsListAdapter extends RecyclerView.Adapter<OutfitsListFragment.OutfitRowViewHolder> {
        private OutfitsListFragment.OnItemClickListener listener;

        void setOnItemClickListener(OutfitsListFragment.OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public OutfitsListFragment.OutfitRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.outfit_list_row, parent, false);
            OutfitsListFragment.OutfitRowViewHolder viewHolder = new OutfitsListFragment.OutfitRowViewHolder(view, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OutfitsListFragment.OutfitRowViewHolder holder, int position) {
            Outfit outfit = profileOutfitsData.get(position);
            holder.bind(outfit);
        }

        @Override
        public int getItemCount() {
            return profileOutfitsData.size();
        }
    }

}