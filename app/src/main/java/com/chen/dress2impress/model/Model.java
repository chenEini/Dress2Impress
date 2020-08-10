package com.chen.dress2impress.model;

import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();
    ModelFirebase modelFirebase;

    private Model() {
        modelFirebase = new ModelFirebase();
    }

    public List<Outfit> getAllOutfits() {
        List<Outfit> data = AppLocalDb.db.outfitDao().getAll();
        return data;
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    public void getAllOutfits(final Listener<List<Outfit>> listener) {
        AsyncTask<String, String, List<Outfit>> task = new AsyncTask<String, String, List<Outfit>>() {

            @Override
            protected List<Outfit> doInBackground(String... strings) {
                //for (int i = 0; i < 10; i++) {
                //AppLocalDb.db.outfitDao().insertAll(new Outfit("" + i, "title" + i, "url" + i, "description" + i));
                //}
                return AppLocalDb.db.outfitDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Outfit> outfits) {
                super.onPostExecute(outfits);
                if (listener != null) listener.onComplete(outfits);
            }
        };

        task.execute();
    }

    public Outfit getOutfit(String id) {
        return null;
    }

    public void updateOutfit(Outfit outfit) {

    }

    public void deleteOutfit(Outfit outfit) {

    }
}
