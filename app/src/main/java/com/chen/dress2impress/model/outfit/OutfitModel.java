package com.chen.dress2impress.model.outfit;

import com.chen.dress2impress.model.AppLocalDb;

import java.util.List;

public class OutfitModel {
    public static final OutfitModel instance = new OutfitModel();

    private OutfitModel() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    public List<Outfit> getAllOutfits() {
        List<Outfit> data = AppLocalDb.db.outfitDao().getAll();
        return data;
    }

    public void getAllOutfits(final Listener<List<Outfit>> listener) {
        OutfitFirebase.getAllOutfits(listener);

//        AsyncTask<String, String, List<Outfit>> task = new AsyncTask<String, String, List<Outfit>>() {
//            @Override
//            protected List<Outfit> doInBackground(String... strings) {
//                return AppLocalDb.db.outfitDao().getAll();
//            }
//            @Override
//            protected void onPostExecute(List<Outfit> outfits) {
//                super.onPostExecute(outfits);
//                if (listener != null) listener.onComplete(outfits);
//            }
//        };
//        task.execute();
    }

    public Outfit getOutfit(String id) {
        return null;
    }

    public void updateOutfit(Outfit outfit) {

    }

    public void deleteOutfit(Outfit outfit) {

    }
}
