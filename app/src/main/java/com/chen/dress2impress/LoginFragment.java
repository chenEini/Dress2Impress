package com.chen.dress2impress;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chen.dress2impress.model.user.UserModel;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.login_fragment, container, false);

        View loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View buttonView) {
                TextView email = view.findViewById(R.id.login_user_email);
                TextView password = view.findViewById(R.id.login_user_password);

                mViewModel.login(email.getText().toString(), password.getText().toString(), new UserModel.Listener<Boolean>() {
                    @Override
                    public void onComplete(Boolean data) {
                        if (data) {
                            view.findViewById(R.id.login_error_msg).setVisibility(View.INVISIBLE);
                            NavController navController = Navigation.findNavController(view);
                            navController.navigateUp();
                        } else {
                            view.findViewById(R.id.login_error_msg).setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        View navigateToRegistrationButton = view.findViewById(R.id.login_navigate_to_registration_button);
        navigateToRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavDirections directions = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
                navController.navigate(directions);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
}