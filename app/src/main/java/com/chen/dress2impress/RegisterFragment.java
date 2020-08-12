package com.chen.dress2impress;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.dress2impress.model.user.UserModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.register_fragment, container, false);
        View registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                TextView email = view.findViewById(R.id.register_user_email);
                TextView password = view.findViewById(R.id.register_user_password);
                TextView name = view.findViewById(R.id.register_user_name);
                mViewModel.register(
                        email.getText().toString(),
                        password.getText().toString(),
                        name.getText().toString(),
                        new UserModel.Listener<Boolean>() {
                            @Override
                            public void onComplete(Boolean data) {
                                NavController navController = Navigation.findNavController(view);
                                navController.navigateUp();
                            }
                        });
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        // TODO: Use the ViewModel
    }

}