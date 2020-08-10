package com.chen.dress2impress.model.user;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFirebase {

    public static void register(String email, String password, final UserModel.Listener<User> listener) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (listener != null) {
                                User user = createUserFromFirebaseUser(auth.getCurrentUser());
                                listener.onComplete(user);
                            }
                        }
                    }
                });
    }

    public static void signIn(String email, String password, final UserModel.Listener<User> listener) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (listener != null) {
                                User user = createUserFromFirebaseUser(auth.getCurrentUser());
                                listener.onComplete(user);
                            }
                        }
                    }
                });
    }

    public static void signOut(User user) {

    }

    private static User createUserFromFirebaseUser(FirebaseUser firUser) {
        return new User(
                firUser.getUid(),
                firUser.getDisplayName(),
                firUser.getEmail(),
                firUser.getPhotoUrl().toString()
        );
    }

}
