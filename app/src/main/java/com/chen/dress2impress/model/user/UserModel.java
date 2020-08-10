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

    public void signIn(String email, String password, Listener<User> listener) {
        UserFirebase.signIn(email, password, listener);
    }
}
