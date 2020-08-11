package com.chen.dress2impress.model.user;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UserFirebase {

    public static User getCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        return firebaseUser == null ? null : factory(firebaseUser);
    }

    public static void register(final User user, String password, final UserModel.Listener<Boolean> listener) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateUserProfile(user);
                            if (listener != null) {
                                listener.onComplete(true);
                            }
                        }
                    }
                });
    }

    public static void login(String email, String password, final UserModel.Listener<Boolean> listener) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (listener != null) {
                                listener.onComplete(true);
                            }
                        }
                    }
                });
    }

    public static void logout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    private static User factory(FirebaseUser firUser) {
        return new User(
                firUser.getUid(),
                firUser.getDisplayName(),
                firUser.getEmail(),
                firUser.getPhotoUrl().toString()
        );
    }

    private static void updateUserProfile(User user) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.name)
                .setPhotoUri(Uri.parse(user.imageUrl))
                .build();

        firebaseUser.updateProfile(profileUpdates);
    }
}
