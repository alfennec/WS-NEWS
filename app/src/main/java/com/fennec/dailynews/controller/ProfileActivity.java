package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fennec.dailynews.R;
import com.fennec.dailynews.config.UserJson;
import com.fennec.dailynews.controller.ui.profile.ProfileFragment;
import com.fennec.dailynews.entity.User;
import com.fennec.dailynews.repository.UserRepository;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfileActivity extends AppCompatActivity {

    public static ProfileActivity main;
    public static String MY_PREFS_NAME = "first_log";

    public Button button_valide_form;
    public TextView textView_msg;

    public int progress = 0;
    public Handler handler = new Handler();

    public UserJson userJson;
    public User newUser;

    public static Boolean all_Right ;

    public static SweetAlertDialog pDialog;

    public static TextInputLayout input_pass1, input_pass2, input_fname, input_lname, input_email;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        main = this;

        inRegistreForm();

    }

    public void inRegistreForm()
    {
        button_valide_form = (Button) findViewById(R.id.button_valide_form);

        input_pass1 = (TextInputLayout) findViewById(R.id.input_pass1);
        input_pass2 = (TextInputLayout) findViewById(R.id.input_pass2);

        input_fname = (TextInputLayout) findViewById(R.id.input_fname);
        input_lname = (TextInputLayout) findViewById(R.id.input_lname);
        input_email = (TextInputLayout) findViewById(R.id.input_email);

        input_fname.getEditText().setText(UserRepository.main_User.fname);
        input_lname.getEditText().setText(UserRepository.main_User.lname);
        input_email.getEditText().setText(UserRepository.main_User.email);

        input_pass1.getEditText().setText(UserRepository.main_User.password);
        input_pass2.getEditText().setText(UserRepository.main_User.password);

        button_valide_form.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String pass1 = input_pass1.getEditText().getText().toString();
                String pass2 = input_pass2.getEditText().getText().toString();

                Date currentTime = Calendar.getInstance().getTime();

                if(verifyIfBlank(input_fname)
                        && verifyIfBlank(input_email)
                        && verifyIfBlank(input_pass1)
                        && verifyIfBlank(input_pass2))
                {
                    all_Right = true;
                }else
                {
                    all_Right = false;
                }

                if(all_Right)
                {
                    if(pass1.equals(pass2))
                    {
                        UserRepository.main_User.fname = input_fname.getEditText().getText().toString();
                        UserRepository.main_User.lname = input_lname.getEditText().getText().toString();
                        UserRepository.main_User.email = input_email.getEditText().getText().toString();
                        UserRepository.main_User.password = input_email.getEditText().getText().toString();

                        userJson = new UserJson("users?id="+UserRepository.main_User.id, main, "PUT", 3,  UserRepository.main_User);

                        pDialog = new SweetAlertDialog(main, SweetAlertDialog.PROGRESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#3483fb"));
                        pDialog.setTitleText("Loading");
                        pDialog.setCancelable(false);
                        pDialog.show();

                    }else
                    {
                        textView_msg.setText("Retapez votre mot de passe");
                    }
                }
            }
        });
    }

    public static void OnSuccesRegistre()
    {
        pDialog.dismiss();

        Toast.makeText(main, "Edit profile made successfully ! " , Toast.LENGTH_LONG ).show();

        setPref(UserRepository.main_User);
        ProfileFragment.isExisting();
        main.finish();
    }

    public static void OnFailedRegistre()
    {
        pDialog.dismiss();

        Toast.makeText(main, "Error please re-enter your data ! " , Toast.LENGTH_LONG ).show();
    }

    public static boolean verifyIfBlank(TextInputLayout input)
    {
        if (TextUtils.isEmpty(input.getEditText().getText().toString()))
        {
            input.setError("Empty Field !");
            return false;
        }else {
            input.setErrorEnabled(false);
            return true;
        }
    }

    public static void setPref(User current_user)
    {
        SharedPreferences prefs = main.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit= prefs.edit();

        edit.putInt("id", current_user.id);
        edit.putString("email", current_user.email);
        edit.putString("fname", current_user.fname);
        edit.putString("lname", current_user.lname);
        edit.putString("password", current_user.password);
        edit.putString("status", current_user.status);

        edit.commit();
    }
}
