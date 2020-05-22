package com.fennec.dailynews.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fennec.dailynews.R;
import com.fennec.dailynews.config.Constante;
import com.fennec.dailynews.config.UserJson;
import com.fennec.dailynews.entity.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {


    public static RegisterActivity main;

    public Button button_valide_form;
    public TextView textView_msg;

    public int progress = 0;
    public Handler handler = new Handler();

    public UserJson userJson;
    public User newUser;


    public static Boolean all_Right ;

    public static ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        main = this;

        inRegistreForm();
    }

    public void inRegistreForm()
    {
        button_valide_form = (Button) findViewById(R.id.button_valide_form);

        button_valide_form.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextInputLayout input_pass1 = (TextInputLayout) findViewById(R.id.input_pass1);
                TextInputLayout input_pass2 = (TextInputLayout) findViewById(R.id.input_pass2);

                String pass1 = input_pass1.getEditText().getText().toString();
                String pass2 = input_pass2.getEditText().getText().toString();

                TextInputLayout input_fname = (TextInputLayout) findViewById(R.id.input_fname);
                TextInputLayout input_lname = (TextInputLayout) findViewById(R.id.input_lname);
                TextInputLayout input_email = (TextInputLayout) findViewById(R.id.input_email);

                RadioButton radioButton_homme = (RadioButton) findViewById(R.id.radioButton_homme);

                Date currentTime = Calendar.getInstance().getTime();

                if(verifyIfBlank(input_fname)
                        && verifyIfBlank(input_lname)
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
                        int RadioGroupe;

                        if(radioButton_homme.isChecked())
                        {
                            RadioGroupe = 1;
                        }else
                        {
                            RadioGroupe = 0;
                        }

                        newUser = new User(
                                input_fname.getEditText().getText().toString(),
                                input_lname.getEditText().getText().toString(),
                                input_email.getEditText().getText().toString(),
                                input_pass1.getEditText().getText().toString(),
                                "enable",
                                currentTime.toString(),
                                currentTime.toString()
                        );

                        userJson = new UserJson("users", main, "POST", 2, newUser);

                        dialog = ProgressDialog.show(main, "", "Data processing. Please wait ...", true);

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
        dialog.dismiss();

        //Costum_toast("Inscription faite avec succes");

        Toast.makeText(main, "Registration made successfully ! " , Toast.LENGTH_LONG ).show();

        main.finish();
    }

    public static void OnFailedRegistre()
    {
        dialog.dismiss();

        //Costum_toast("Erreur veuillez resaisir vos données");

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
}
