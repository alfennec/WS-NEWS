package com.fennec.dailynews.controller.ui.profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fennec.dailynews.R;
import com.fennec.dailynews.controller.HomeActivity;
import com.fennec.dailynews.controller.LoginActivity;
import com.fennec.dailynews.controller.ProfileActivity;
import com.fennec.dailynews.controller.RegisterActivity;
import com.fennec.dailynews.repository.UserRepository;

import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileFragment extends Fragment {

    private ProfileViewModel videoViewModel;

    public static ProfileFragment main;

    public static View root;

    public static Button btn_register, btn_login, btn_lougout, btn_editProfile;

    public static Button btn_pp, btn_rus, btn_mapp, btn_about;

    public static TextView tv_welcome, tv_under;

    public static AlertDialog alertDialog;

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


        /**** open page ***/

        btn_pp = (Button) root.findViewById(R.id.btn_pp);
        btn_rus = (Button) root.findViewById(R.id.btn_rus);
        btn_mapp = (Button) root.findViewById(R.id.btn_mapp);
        btn_about = (Button) root.findViewById(R.id.btn_about);

        btn_pp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(main.getActivity(), "Privacy Policy","Le lorem ipsum est, en imprimerie, une suite de mots sans signification utilisée à titre provisoire pour calibrer une mise en page, le texte définitif venant remplacer le faux-texte dès qu'il est prêt ou que la mise en page est achevée. Généralement, on utilise un texte en faux latin, le Lorem ipsum ou Lipsum");
            }
        });

        btn_rus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(main.getContext(), "Rate us link here !", Toast.LENGTH_LONG).show();
            }
        });

        btn_mapp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(main.getContext(), "More app link here !", Toast.LENGTH_LONG).show();
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDialog(main.getActivity(), "About us","We are Eradroids, a creative and dedicated group of individuals who love Android apps almost as much as we love our customers, we develop high quality Native Android apps template designed for you. Build Android apps made it simple.");
            }
        });


        return root;
    }

    public static void showDialog(Activity activity, String title, String msg)
    {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.costum_dialog);

        TextView title_dialog = (TextView) dialog.findViewById(R.id.title_dialog);
        title_dialog.setText(title);

        TextView text = (TextView) dialog.findViewById(R.id.content_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dissmiss);
        dialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public static void isExisting()
    {
        String subResult = UserRepository.main_User.lname.substring(0,1);
        subResult = subResult.toUpperCase();

        tv_welcome.setText("Welcome, "+ subResult+"."+UserRepository.main_User.fname);
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