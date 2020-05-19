package com.fennec.dailynews.repository;

import android.util.Log;

import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.entity.Comments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentsRepository {

    public static ArrayList<Comments> list_comments = new ArrayList<>();

    public static boolean ParseData(String result)
    {
        try
        {
            JSONArray jArray = new JSONArray(result);

            for (int i=0; i < jArray.length(); i++)
            {
                Comments json_comments = new Comments();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_comments.id                = Integer.parseInt(oneObject.getString("id"));
                    json_comments.id_user           = oneObject.getString("id_user");
                    json_comments.id_news           = Integer.parseInt(oneObject.getString("id_news"));
                    json_comments.message           = oneObject.getString("message");
                    json_comments.created           = oneObject.getString("created");
                    json_comments.modified          = oneObject.getString("modified");

                }
                catch (JSONException e)
                {
                    Log.e("tag_json ", "parse data category 1 "+e);
                }

                list_comments.add(json_comments);
            }

            return true;
        }
        catch (Exception e)
        {
            Log.e("tag_json", "parse data category 2 "+e);
        }

        return false;
    }

}
