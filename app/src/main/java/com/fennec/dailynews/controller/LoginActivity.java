package com.fennec.dailynews.controller;

        import androidx.appcompat.app.AppCompatActivity;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.Handler;
        import android.text.TextUtils;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ProgressBar;
        import android.widget.Toast;

        import com.fennec.dailynews.R;
        import com.fennec.dailynews.config.Constante;
        import com.fennec.dailynews.config.UserJson;
        import com.fennec.dailynews.controller.ui.profile.ProfileFragment;
        import com.fennec.dailynews.entity.User;
        import com.fennec.dailynews.repository.UserRepository;
        import com.google.android.material.textfield.TextInputLayout;

        import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    public static LoginActivity main;
    public static String MY_PREFS_NAME = "first_log";

    public ProgressBar progressBar2;
    public int progress = 0;

    TextInputLayout input_email;
    TextInputLayout input_pass;

    public static ProgressDialog dialog;

    public static SweetAlertDialog pDialog;

    public Handler handler = new Handler();

    public User jsonUser;

    public Button btn_forget, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        main = this;

        input_email = (TextInputLayout) findViewById(R.id.input_email);
        input_pass  = (TextInputLayout) findViewById(R.id.input_pass);

        Button Button_valider = (Button) findViewById(R.id.button_valide_form);

        btn_forget = (Button) findViewById(R.id.btn_forget);
        btn_register = (Button) findViewById(R.id.btn_register);


        Button_valider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(verifyIfBlank(input_email) && verifyIfBlank(input_pass))
                {
                    String url_informations = "users";

                    String email = "?email="+input_email.getEditText().getText().toString();
                    String pass = "&password="+input_pass.getEditText().getText().toString();

                    jsonUser = new User(input_email.getEditText().getText().toString(), input_pass.getEditText().getText().toString());

                    url_informations +=email+pass;

                    UserJson userJson = new UserJson(url_informations, main, "GET", 1, jsonUser);

                    //dialog = ProgressDialog.show(main, "", "Traitement de données. Veulliez attendre ...", true);

                    pDialog = new SweetAlertDialog(main, SweetAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Loading");
                    pDialog.setCancelable(false);
                    pDialog.show();

                }else
                {
                    OnFailedLogin();
                }
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, ForgetPassActivity.class);
                main.startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(main, RegisterActivity.class);
                startActivity(intent);
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

        Toast.makeText(main, " Connexion made with succes ! " , Toast.LENGTH_LONG ).show();

        SharedPreferences prefs = main.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit= prefs.edit();

        User current_user = UserRepository.main_User;

        edit.putInt("id", current_user.id);
        edit.putString("email", current_user.email);
        edit.putString("fname", current_user.fname);
        edit.putString("lname", current_user.lname);
        edit.putString("password", current_user.password);
        edit.putString("status", current_user.status);

        edit.commit();

        ProfileFragment.isExisting();

        UserRepository.EXIST = true;

        main.finish();

    }

    public static void OnFailedLogin()
    {
        pDialog.dismiss();
        Toast.makeText(main, "Incorrect email or password !" , Toast.LENGTH_LONG ).show();
    }
}
