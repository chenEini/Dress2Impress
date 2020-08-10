package com.chen.dress2impress.model.user;

public class UserModel {
    public static final UserModel instance = new UserModel();

    private UserModel() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public interface CompListener {
        void onComplete();
    }

    public void signUp(User user) {
        UserFirebase.signUp(user);
    }
}
