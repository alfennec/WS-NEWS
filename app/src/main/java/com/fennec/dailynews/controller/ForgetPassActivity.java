package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fennec.dailynews.R;
import com.fennec.dailynews.config.ForgetJson;
import com.fennec.dailynews.config.UserJson;
import com.fennec.dailynews.controller.ui.profile.ProfileFragment;
import com.fennec.dailynews.entity.User;
import com.fennec.dailynews.repository.UserRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ForgetPassActivity extends AppCompatActivity {

    public static TextInputLayout input_email;
    public static ForgetPassActivity main;
    public User jsonUser;

    public static SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        main = this;

        input_email = (TextInputLayout) findViewById(R.id.input_email);

        Button button_valide_form = (Button) findViewById(R.id.button_valide_form);

        button_valide_form.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(verifyIfBlank(input_email))
                {
                    String url_informations = "forget_pass";

                    String email = "?email="+input_email.getEditText().getText().toString();

                    jsonUser = new User(input_email.getEditText().getText().toString());

                    url_informations +=email;

                    ForgetJson userJson = new ForgetJson(url_informations, main, "GET", 1, jsonUser);

                    pDialog = new SweetAlertDialog(main, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    OnSuccesLogin();

                }else
                {
                    OnFailedLogin();
                }
            }
        });
    }

    public static boolean verifyIfBlank(TextInputLayout input)
    {
        if (TextUtils.isEmpty(input.getEditText().getText().toString()))
        {
            input.setError("Champs vide");
            return false;
        }else {
            input.setErrorEnabled(false);
            return true;
        }
    }

    public static void OnSuccesLogin()
    {
        //dialog.dismiss();
        pDialog.dismiss();

        new SweetAlertDialog(main, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Success !!")
                .setContentText("Password has been sent to your email !")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {
                        sDialog.dismissWithAnimation();
                        main.finish();
                    }
                })
                .show();
    }

    public static void OnFailedLogin()
    {
        pDialog.dismiss();
        Toast.makeText(main, " " , Toast.LENGTH_LONG ).show();

        new SweetAlertDialog(main, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Failed !!")
                .setContentText("Email don't exist in the database ! !")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener()
                {
                    @Override
                    public void onClick(SweetAlertDialog sDialog)
                    {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
}
