package com.fennec.dailynews.config;

import android.content.Context;
import android.util.Log;

import com.fennec.dailynews.controller.ForgetPassActivity;
import com.fennec.dailynews.controller.LoginActivity;
import com.fennec.dailynews.controller.ProfileActivity;
import com.fennec.dailynews.controller.RegisterActivity;
import com.fennec.dailynews.entity.User;
import com.fennec.dailynews.repository.UserRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserJson {

    public int choice_acty;

    public UserJson(String link, final Context ctx, String method, int choice_acty, User user)
    {
        this.choice_acty = choice_acty;

        String Send_link = Constante.url_host+link;

        Log.d("TAG", "TAG-JSON-USER: "+Send_link);

        Ion.with(ctx)
                .load(method, Send_link)
                .setBodyParameter("id",""+user.id)
                .setBodyParameter("fname",user.fname)
                .setBodyParameter("lname",user.lname)
                .setBodyParameter("email",user.email)
                .setBodyParameter("password",user.password)
                .setBodyParameter("status",user.status)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        Log.e("TAG-JSON-USER", "onSuccess: result " + e +" --- "+result);

                        if(result != null)
                        {
                            mainFunction(result);
                            Log.e("TAG_JSON-2", "onSuccess: result " + result );
                        }else
                        {
                            onFailed(e);
                            Log.e("TAG_JSON", "onFailed: error " + result );
                        }
                    }
                });
    }

    public void onFailed(Exception e)
    {
        Log.e("TAG_JSON", "onFailed: error " + e );
    }

    public void mainFunction(String result)
    {
        switch (choice_acty)
        {
            case 1: getSuccess(result); break;

            case 2: postSuccess(result); break;

            case 3: editSuccess(result); break;

            case 4: forgetPass(result); break;

            default : break;
        }
    }

    public void getSuccess(String result)
    {
        if(UserRepository.ParseData(result))
        {
            Log.e("TAG_LOGIN_FORM",  result );
            LoginActivity.OnSuccesLogin();
        }else
            {
                LoginActivity.OnFailedLogin();
            }
    }

    public void editSuccess(String result)
    {
        String myResult = "["+result+"]";
        int status = 0;
        String status_message;

        try
        {
            JSONArray jArray = new JSONArray(myResult);

            for (int i=0; i < jArray.length(); i++)
            {
                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    status          = Integer.parseInt(oneObject.getString("status"));
                    status_message  = oneObject.getString("status_message");
                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("tag_json", ""+e);
        }


        if(status == 1)
        {
            ProfileActivity.OnSuccesRegistre();
        }else
        {
            ProfileActivity.OnFailedRegistre();
        }
    }

    public void postSuccess(String result)
    {
        String myResult = "["+result+"]";
        int status = 0;
        String status_message;

        try
        {
            JSONArray jArray = new JSONArray(myResult);

            for (int i=0; i < jArray.length(); i++)
            {
                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    status          = Integer.parseInt(oneObject.getString("status"));
                    status_message  = oneObject.getString("status_message");
                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("tag_json", ""+e);
        }


        if(status == 1)
        {
            RegisterActivity.OnSuccesRegistre();
        }else
            {
                RegisterActivity.OnFailedRegistre();
            }
    }

    public void deleteSuccess()
    {

    }

    public void forgetPass(String result)
    {
        String myResult = "["+result+"]";
        int status = 0;
        String status_message;

        try
        {
            JSONArray jArray = new JSONArray(myResult);

            for (int i=0; i < jArray.length(); i++)
            {
                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    status          = Integer.parseInt(oneObject.getString("status"));
                    status_message  = oneObject.getString("status_message");
                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("tag_json", ""+e);
        }


        if(status == 1)
        {
            ForgetPassActivity.OnSuccesLogin();
        }else
        {
            ForgetPassActivity.OnFailedLogin();
        }
    }
}
