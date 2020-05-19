package com.fennec.dailynews.config;

import android.content.Context;
import android.util.Log;

import com.fennec.dailynews.controller.CommentsActivity;
import com.fennec.dailynews.entity.Comments;
import com.fennec.dailynews.repository.CommentsRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentsJson {
    public String method;
    public int choice_acty;

    public CommentsJson(String link , final Context ctx, String method, int choice_acty, Comments comments)
    {
        this.method = method;
        this.choice_acty = choice_acty;

        String Send_link = Constante.url_host+link;

        Log.d("TAG-COMMENT", "CATEGORY JSON: "+Send_link);

        Ion.with(ctx)
                .load(method, Send_link)
                .setBodyParameter("id_user",""+comments.id_user)
                .setBodyParameter("id_news",""+comments.id_news)
                .setBodyParameter("message",comments.message)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            mainFunction(result);
                            Log.e("TAG-COMMENT", "onSuccess: result " + result );
                        }else
                        {
                            onFailed(e);
                            Log.e("TAG-COMMENT", "onFailed: error " + result );
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

            case 2: PostSuccess(result); break;

            case 3: DeleteSuccess(result); break;

            default : break;
        }
    }

    public void getSuccess(String result)
    {
        if(CommentsRepository.ParseData(result))
        {
            CommentsActivity.onSucces();
        }
    }

    public void PostSuccess(String result)
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
            CommentsActivity.OnSuccesPost();
        }else
        {
            CommentsActivity.OnFailedPost();
        }
    }

    public void DeleteSuccess(String result)
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
            CommentsActivity.OnSuccesPost();
        }else
        {
            CommentsActivity.OnFailedPost();
        }
    }

}

