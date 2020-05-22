package com.fennec.dailynews.repository;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.fennec.dailynews.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BookMarkRepository {

    public static ArrayList<News> list_news = new ArrayList<>();

    public static boolean ifExist(News current_news)
    {
        for (int i = 0; i < list_news.size(); i++)
        {
            if(list_news.get(i).id == current_news.id)
            {
                return true;
            }
        }
        return false;
    }

    public static String read(Context context, String fileName)
    {
        try
        {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                sb.append(line);
            }
            return sb.toString();
        }
        catch (FileNotFoundException fileNotFound)
        {
            return null;
        }
        catch (IOException ioException)
        {
            return null;
        }
    }

    public static boolean create(Context context, String fileName, String jsonString)
    {
        String FILENAME = "storage.json";
        try
        {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null)
            {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound)
        {
            return false;
        } catch (IOException ioException)
        {
            return false;
        }

    }

    public static boolean isFilePresent(Context context, String fileName)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }

    public static String ArrayToJson()
    {
        String current_data;

        JSONArray array = new JSONArray();

        for (int i =0; i < list_news.size() ; i++ )
        {
            JSONObject object = new JSONObject();
            try
            {
                object.put("id", list_news.get(i).id);
                object.put("id_category" , list_news.get(i).id_category);
                object.put("title" , list_news.get(i).title);
                object.put("date_news" , list_news.get(i).date_news);
                object.put("content_type" , list_news.get(i).content_type);
                object.put("description" , list_news.get(i).description);
                object.put("news_photo" , list_news.get(i).news_photo);
                object.put("news_video" , list_news.get(i).news_video);
                object.put("news_link" , list_news.get(i).news_link);
                object.put("wname" , list_news.get(i).wname);
                object.put("created" , list_news.get(i).created);
                object.put("modified" , list_news.get(i).modified);
                object.put("nbr_comments" , list_news.get(i).nbr_comments);
                array.put(object);

            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        current_data = array.toString();
        return current_data;
    }

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
                    json_news.wname             = oneObject.getString("wname");
                    json_news.created           = oneObject.getString("created");
                    json_news.modified          = oneObject.getString("modified");
                    json_news.nbr_comments      = Integer.parseInt(oneObject.getString("nbr_comments"));

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

    public static News getById(int id)
    {
        News current_news = new News();

        for (int i = 0; i < list_news.size(); i++)
        {
            if(list_news.get(i).id == id)
            {
                current_news = list_news.get(i);
            }
        }

        return current_news;
    }
}
