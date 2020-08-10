package com.chen.dress2impress;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.dress2impress.model.Model;
import com.chen.dress2impress.model.Outfit;

import java.util.LinkedList;
import java.util.List;

public class OutfitsListFragment extends Fragment {
    RecyclerView outfitsList;
    OutfitsListAdapter adapter;
    List<Outfit> outfitsData = new LinkedList<Outfit>();

    Delegate parent;

    interface Delegate {
        void onItemSelected(Outfit outfit);
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public OutfitsListFragment() {
        Model.instance.getAllOutfits(new Model.Listener<List<Outfit>>() {
            @Override
            public void onComplete(List<Outfit> data) {
                outfitsData = data;
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static OutfitsListFragment newInstance(String param1, String param2) {
        OutfitsListFragment fragment = new OutfitsListFragment();
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parent = null;
    }

    static class OutfitRowViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;

        public OutfitRowViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.outfit_row_title);
            description = itemView.findViewById(R.id.outfit_row_description);

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