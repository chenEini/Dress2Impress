package com.chen.dress2impress;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chen.dress2impress.model.Model;
import com.chen.dress2impress.model.Outfit;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;
    List<Outfit> outfitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.outfit_list_view);

        outfitList = Model.instance.getAllOutfits();

        OutfitListAdapter adapter = new OutfitListAdapter();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("TAG", "click on row number:" + i);
            }
        });
    }

    class OutfitListAdapter extends BaseAdapter {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        @Override
        public int getCount() {
            return outfitList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.outfit_list_row, null);
            }

            Outfit outfit = outfitList.get(i);

            TextView title = view.findViewById(R.id.outfit_row_title);
            TextView description = view.findViewById(R.id.outfit_row_description);

            title.setText(outfit.title);
            description.setText(outfit.description);

            return view;
        }
    }
}