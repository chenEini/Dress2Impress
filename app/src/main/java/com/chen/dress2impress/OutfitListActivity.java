package com.chen.dress2impress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.dress2impress.model.Model;
import com.chen.dress2impress.model.Outfit;

import java.util.List;

public class OutfitListActivity extends AppCompatActivity {
    RecyclerView outfitsList;
    List<Outfit> outfitsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outfit_list);

        outfitsData = Model.instance.getAllOutfits();

        outfitsList = findViewById(R.id.outfits_list);
        outfitsList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        outfitsList.setLayoutManager(layoutManager);

        OutfitsListAdapter adapter = new OutfitsListAdapter();
        outfitsList.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("TAG", "Row number" + position + "was clicked");
            }
        });
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
            View view = LayoutInflater.from(OutfitListActivity.this).inflate(R.layout.outfit_list_row, parent, false);
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