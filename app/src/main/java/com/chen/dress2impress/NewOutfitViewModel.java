package com.chen.dress2impress;

import androidx.lifecycle.ViewModel;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.outfit.OutfitModel;
import com.chen.dress2impress.model.user.User;
import com.chen.dress2impress.model.user.UserFirebase;
import com.chen.dress2impress.model.user.UserModel;

public class NewOutfitViewModel extends ViewModel {

    public User getCurrentUser() {
        return UserModel.instance.getCurrentUser();
    }

    public void addOrUpdateOutfit(Outfit outfit, OutfitModel.CompleteListener listener) {
        OutfitModel.instance.addOrUpdateOutfit(outfit, listener);
    }
}
