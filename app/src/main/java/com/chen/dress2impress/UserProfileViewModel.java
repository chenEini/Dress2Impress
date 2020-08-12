package com.chen.dress2impress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.outfit.OutfitModel;
import com.chen.dress2impress.model.user.UserModel;

import java.util.List;

public class UserProfileViewModel extends ViewModel {
    private LiveData<List<Outfit>> liveData;

    public LiveData<List<Outfit>> getData() {
        if (liveData == null) {
//            liveData = OutfitModel.instance.getAllOutfits();
            liveData = OutfitModel.instance.getUserOutfits(UserModel.instance.getCurrentUser());
        }
        return liveData;
    }

    public void logout() {
        UserModel.instance.logout();
    }
}
