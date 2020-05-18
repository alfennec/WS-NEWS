package com.fennec.dailynews.repository;

import android.util.Log;

import com.fennec.dailynews.entity.News;
import com.fennec.dailynews.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserRepository {

    public static boolean EXIST ;

    public static User main_User = new User();

    public static boolean ParseData(String response)
    {
        String GetResult = "["+response+"]";
        int status = 0;
        String status_message ="";
        String result ="";

        try
        {
            JSONArray jArray = new JSONArray(GetResult);

            for (int i = 0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    status          = Integer.parseInt(oneObject.getString("status"));
                    status_message  = oneObject.getString("status_message");
                    result          = oneObject.getString("result");
                } catch (JSONException e)
                {
                    Log.e("tag_json", "" + e);
                }
            }
        } catch (Exception e)
        {
            Log.e("tag_json", "" + e);
        }

        if (status == 1)
        {
            try
            {
                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++)
                {
                    User json_user = new User();

                    try
                    {
                        JSONObject oneObject = jArray.getJSONObject(i);

                        json_user.id = Integer.parseInt(oneObject.getString("id"));
                        json_user.name = oneObject.getString("name");
                        json_user.email = oneObject.getString("email");
                        json_user.password = oneObject.getString("password");
                        json_user.status = oneObject.getString("status");
                        json_user.created = oneObject.getString("created");
                        json_user.modified = oneObject.getString("modified");
                    } catch (JSONException e) {
                        Log.e("tag_json", "" + e);
                    }

                    main_User = json_user;
                }

                return true;

            } catch (Exception e)
            {
                Log.e("tag_json", "" + e);
            }

            return false;
        }
        return false;
    }

}
