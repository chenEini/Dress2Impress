package com.chen.dress2impress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.outfit.OutfitModel;

import java.util.List;

public class OutfitListViewModel extends ViewModel {
    private LiveData<List<Outfit>> liveData;

    public LiveData<List<Outfit>> getData() {
        if (liveData == null) {
            liveData = OutfitModel.instance.getAllOutfits();
        }
        return liveData;
    }

    public void refresh(OutfitModel.CompleteListener listener) {
        OutfitModel.instance.refreshOutfitsList(listener);
    }
}
