package com.chen.dress2impress;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chen.dress2impress.model.outfit.OutfitModel;
import com.chen.dress2impress.model.outfit.Outfit;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class OutfitsListFragment extends Fragment {
    OutfitsListAdapter adapter;
    RecyclerView outfitsList;
    List<Outfit> outfitsData = new LinkedList<Outfit>();
    private OutfitsListViewModel viewModel;

    Delegate parent;

    interface Delegate {
        void onItemSelected(Outfit outfit);
    }

    public OutfitsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outfits_list, container, false);

        outfitsList = view.findViewById(R.id.outfits_list);
        outfitsList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        outfitsList.setLayoutManager(layoutManager);

        adapter = new OutfitsListAdapter();
        outfitsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "You clicked on row number " + position);
                Outfit outfit = outfitsData.get(position);
                parent.onItemSelected(outfit);
            }
        });

        final SwipeRefreshLayout swipeRefresh = view.findViewById(R.id.outfit_list_swipe_refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.refresh(new OutfitModel.CompleteListener() {
                    @Override
                    public void onComplete() {
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });

        LiveData<List<Outfit>> liveData = viewModel.getData();
        liveData.observe(getViewLifecycleOwner(), new Observer<List<Outfit>>() {
            @Override
            public void onChanged(List<Outfit> outfits) {
                outfitsData = outfits;
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Delegate) {
            parent = (Delegate) getActivity();
        } else {
            throw new RuntimeException(context.toString()
                    + "outfit list parent activity must implement delegate");
        }

        setHasOptionsMenu(true);

        viewModel = new ViewModelProvider(this).get(OutfitsListViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.outfits_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_outfit_list_add:
                NavController navController = Navigation.findNavController(outfitsList);
                NavDirections directions = NewOutfitFragmentDirections.actionGlobalNewOutfitFragment();
                navController.navigate(directions);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class OutfitRowViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView image;

        public OutfitRowViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.outfit_row_title);
            description = itemView.findViewById(R.id.outfit_row_description);
            image = itemView.findViewById(R.id.outfit_row_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                    }
                }
            });
        }

        public void bind(Outfit outfit) {
            title.setText(outfit.title);
            description.setText(outfit.description);
            if (outfit.imageUrl != null && outfit.imageUrl != "")
                Picasso.get().load(outfit.imageUrl).placeholder(R.drawable.outfit).into(image);
            else
                image.setImageResource(R.drawable.outfit);
        }
    }

    interface OnItemClickListener {
        void onClick(int position);
    }

    class OutfitsListAdapter extends RecyclerView.Adapter<OutfitRowViewHolder> {
        private OnItemClickListener listener;

        void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public OutfitRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.outfit_list_row, parent, false);
            OutfitRowViewHolder viewHolder = new OutfitRowViewHolder(view, listener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull OutfitRowViewHolder holder, int position) {
            Outfit outfit = outfitsData.get(position);
            holder.bind(outfit);
        }

        @Override
        public int getItemCount() {
            return outfitsData.size();
        }
    }
}