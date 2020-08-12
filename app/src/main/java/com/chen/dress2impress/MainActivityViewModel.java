package com.chen.dress2impress;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import com.chen.dress2impress.model.outfit.Outfit;
import com.chen.dress2impress.model.user.UserModel;

public class MainActivityViewModel extends ViewModel {

    public boolean isUserLoggedIn() {
        return UserModel.instance.isUserLoggedIn();
    }

    public Bundle createBundle() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("Outfit", new Outfit());
        return bundle;
    }
}
