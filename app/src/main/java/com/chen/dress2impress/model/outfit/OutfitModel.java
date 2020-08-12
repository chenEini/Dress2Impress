package com.chen.dress2impress.model.outfit;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.chen.dress2impress.MyApplication;
import com.chen.dress2impress.model.AppLocalDb;
import com.chen.dress2impress.model.user.User;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OutfitModel {
    public static final OutfitModel instance = new OutfitModel();

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompleteListener {
        void onComplete();
    }

    private OutfitModel() {
    }

    public LiveData<List<Outfit>> getAllOutfits() {
        LiveData<List<Outfit>> liveData = AppLocalDb.db.outfitDao().getAll();
        refreshOutfitsList(null);
        return liveData;
    }

    public void refreshOutfitsList(final CompleteListener listener) {
        long lastUpdated = MyApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).getLong("OutfitsLastUpdateDate", 0);

        OutfitFirebase.getAllOutfitsSince(lastUpdated, new Listener<List<Outfit>>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final List<Outfit> data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        long lastUpdated = 0;
                        for (Outfit outfit : data) {
                            AppLocalDb.db.outfitDao().insertAll(outfit);
                            if (outfit.lastUpdated > lastUpdated) lastUpdated = outfit.lastUpdated;
                        }
                        SharedPreferences.Editor edit = MyApplication.context.getSharedPreferences("TAG", MODE_PRIVATE).edit();
                        edit.putLong("OutfitsLastUpdateDate", lastUpdated);
                        edit.commit();
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (listener != null) listener.onComplete();
                    }
                }.execute("");
            }
        });
    }

    public LiveData<List<Outfit>> getUserOutfits(User currentUser) {
        return AppLocalDb.db.outfitDao().getUserOutfits(currentUser.id);
    }

    public void addOutfit(Outfit outfit, final CompleteListener listener) {
        OutfitFirebase.addOutfit(outfit, new Listener<Outfit>() {
            @Override
            public void onComplete(Outfit data) {
                refreshOutfitsList(listener);
            }
        });
    }

    public Outfit getOutfit(String id) {
        return null;
    }

    public void updateOutfit(Outfit outfit) {
    }

    public void deleteOutfits(final List<Outfit> outfits, final Listener<Object> listener) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                for (Outfit outfit : outfits) {
                    AppLocalDb.db.outfitDao().delete(outfit);
                }
                return "";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (listener != null) listener.onComplete(null);
            }
        }.execute("");
    }
}
