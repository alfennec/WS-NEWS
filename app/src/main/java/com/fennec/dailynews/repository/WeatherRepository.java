package com.fennec.dailynews.repository;

import android.util.Log;

import com.fennec.dailynews.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherRepository {

    public static int temp;
    public static String img;

    public static String weather;
    public static String main;

    public static boolean ParseData(String result)
    {
        int sizeResult = result.length();
        String subResult = result.substring(5,sizeResult-1);

        subResult = "["+subResult+"]";

        Log.e("TAG_WEATHER", "onSuccess: " + subResult );

        try
        {
            JSONArray jArray = new JSONArray(subResult);

            for (int i=0; i < jArray.length(); i++)
            {
                try
                {
                    JSONObject oneObject = jArray.getJSONObject(i);

                    //temp  = Integer.parseInt(oneObject.getString("temp"));
                    //img   = oneObject.getString("icon");

                    /************************ weather ***************/
                    weather   = oneObject.getString("weather");
                    try
                    {
                        JSONArray jArray2 = new JSONArray(weather);

                        for (int j=0; j < jArray2.length(); j++)
                        {
                            try
                            {
                                JSONObject oneObject2 = jArray2.getJSONObject(j);

                                img   = oneObject2.getString("icon");
                            }
                            catch (JSONException e)
                            {
                                Log.e("tag_json", ""+e);
                            }

                            Log.e("TAG_WEATHER", "onSuccess: result " + img );
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("TAG_WEATHER", ""+e);
                    }

                    /************************ temperetur ***************/
                    main   = oneObject.getString("main");
                    main = "["+main+"]";
                    try
                    {
                        JSONArray jArray2 = new JSONArray(main);

                        for (int j=0; j < jArray2.length(); j++)
                        {
                            try
                            {
                                JSONObject oneObject2 = jArray2.getJSONObject(j);

                                Double DoubleTemp  = Double.parseDouble(oneObject2.getString("temp"));
                                temp  = (int)(DoubleTemp - 273.15);
                            }
                            catch (JSONException e)
                            {
                                Log.e("tag_json", ""+e);
                            }

                            Log.e("TAG_WEATHER", "onSuccess: result " + temp );
                        }
                    }
                    catch (Exception e)
                    {
                        Log.e("TAG_WEATHER", ""+e);
                    }
                }
                catch (JSONException e)
                {
                    Log.e("tag_json", ""+e);
                }

                Log.e("TAG_WEATHER", "onSuccess: result " + main );
            }

            return true;
        }
        catch (Exception e)
        {
            Log.e("TAG_WEATHER", ""+e);
        }

        return false;
    }
}
