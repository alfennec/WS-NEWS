package com.fennec.dailynews.controller.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fennec.dailynews.R;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.LoginActivity;
import com.fennec.dailynews.controller.ProfileActivity;
import com.fennec.dailynews.controller.RegisterActivity;
import com.fennec.dailynews.repository.UserRepository;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {

    private ProfileViewModel videoViewModel;

    public static ProfileFragment main;

    public static View root;

    public static Button btn_register, btn_login, btn_lougout, btn_editProfile;

    public static TextView tv_welcome, tv_under;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        videoViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        main = this;

        tv_welcome = (TextView) root.findViewById(R.id.tv_welcome);
        tv_under = (TextView) root.findViewById(R.id.tv_under);
        btn_login =(Button) root.findViewById(R.id.btn_login);
        btn_register = (Button) root.findViewById(R.id.btn_register);

        btn_lougout = (Button) root.findViewById(R.id.btn_lougout);
        btn_editProfile = (Button) root.findViewById(R.id.btn_editProfile);

        if(UserRepository.EXIST)
        {
            isExisting();
        }else
            {
                notExisting();
            }


        return root;
    }

    public static void isExisting()
    {
        tv_welcome.setText("Welcome, "+ UserRepository.main_User.fname);
        tv_under.setText("Edit you profile infos ");

        btn_login.setVisibility(View.GONE);
        btn_register.setVisibility(View.GONE);

        btn_lougout.setVisibility(View.VISIBLE);
        btn_editProfile.setVisibility(View.VISIBLE);

        btn_lougout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                new SweetAlertDialog(main.getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                        {
                            @Override
                            public void onClick(SweetAlertDialog sDialog)
                            {
                                HomeActivity.quitter();
                                notExisting();

                                UserRepository.EXIST = false;

                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        btn_editProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main.getContext(), ProfileActivity.class);
                main.startActivity(i);
            }
        });
    }

    public static void notExisting()
    {
        tv_welcome.setText("Welcome, Dear");
        tv_under.setText("Not Member ?");

        btn_lougout.setVisibility(View.GONE);
        btn_editProfile.setVisibility(View.GONE);

        btn_login.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.VISIBLE);

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main.getContext(), RegisterActivity.class);
                main.startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(main.getContext(), LoginActivity.class);
                main.startActivity(i);
            }
        });
    }

}