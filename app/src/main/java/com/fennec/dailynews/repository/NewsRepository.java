package com.fennec.dailynews.repository;

import android.util.Log;

import com.fennec.dailynews.entity.Category;
import com.fennec.dailynews.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsRepository {

    public static ArrayList<News> list_news = new ArrayList<>();


    public static boolean ParseData(String result)
    {
        try
        {
            JSONArray jArray = new JSONArray(result);

            for (int i=0; i < jArray.length(); i++)
            {
                News json_news = new News();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_news.id                = Integer.parseInt(oneObject.getString("id"));
                    json_news.id_category       = Integer.parseInt(oneObject.getString("id_category"));
                    json_news.title             = oneObject.getString("title");
                    json_news.date_news         = oneObject.getString("date_news");
                    json_news.content_type      = oneObject.getString("content_type");
                    json_news.description       = oneObject.getString("description");
                    json_news.news_photo        = oneObject.getString("news_photo");
                    json_news.news_video        = oneObject.getString("news_video");
                    json_news.news_link         = oneObject.getString("news_link");
                    json_news.created           = oneObject.getString("created");
                    json_news.modified          = oneObject.getString("modified");


                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }

                list_news.add(json_news);
            }

            return true;
        }
        catch (Exception e)
        {
            Log.e("tag_json", ""+e);
        }

        return false;
    }
}
