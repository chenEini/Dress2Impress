package com.chen.dress2impress;

import androidx.lifecycle.ViewModel;

import com.chen.dress2impress.model.user.User;
import com.chen.dress2impress.model.user.UserModel;

public class LoginViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public void login(String email, String password, UserModel.Listener listener) {
        UserModel.instance.login(email, password, listener);
    }
}