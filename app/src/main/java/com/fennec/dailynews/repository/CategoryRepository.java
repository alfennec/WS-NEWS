package com.fennec.dailynews.repository;

import android.util.Log;

import com.fennec.dailynews.entity.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryRepository {

    public static ArrayList<Category> list_category = new ArrayList<>();

    public static Category getById(int id)
    {
        Category current_category = new Category();

        for (int i = 0; i < list_category.size(); i++)
        {
          if(list_category.get(i).id == id)
          {
              current_category = list_category.get(i);
          }
        }

        return current_category;
    }

    public static boolean ParseData(String result)
    {
        try
        {
            JSONArray jArray = new JSONArray(result);

            for (int i=0; i < jArray.length(); i++)
            {
                Category json_category = new Category();

                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    json_category.id                = Integer.parseInt(oneObject.getString("id"));
                    json_category.name              = oneObject.getString("name");
                    json_category.image             = oneObject.getString("image");
                    json_category.created           = oneObject.getString("created");
                    json_category.modified          = oneObject.getString("modified");


                }
                catch (JSONException e)
                {
                    Log.e("tag_json ", "parse data category 1 "+e);
                }

                list_category.add(json_category);
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
