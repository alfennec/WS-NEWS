package com.fennec.dailynews.controller.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fennec.dailynews.R;
import com.fennec.dailynews.controller.NewsActivity;
import com.fennec.dailynews.controller.RegisterActivity;

public class ProfileFragment extends Fragment {

    private ProfileViewModel videoViewModel;

    public static ProfileFragment main;

    public Button btn_register;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        videoViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        main = this;

        btn_register = (Button) root.findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main.getContext(), RegisterActivity.class);
                main.startActivity(i);
            }
        });

        return root;
    }
}