package com.chen.dress2impress;

import androidx.lifecycle.ViewModel;

import com.chen.dress2impress.model.user.User;
import com.chen.dress2impress.model.user.UserModel;

public class RegisterViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public void register(String email, String password, String name, UserModel.Listener listener) {
        User user = new User(name, email);
        UserModel.instance.register(user, password, listener);
    }
}