package com.fennec.dailynews.config;

import android.content.Context;
import android.util.Log;

import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.repository.NewsRepository;
import com.fennec.dailynews.repository.WeatherRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class WeatherJson
{

    public String method;
    public int choice_acty;

    public WeatherJson(String city , final Context ctx, String method, int choice_acty)
    {
        this.method = method;
        this.choice_acty = choice_acty;

        String Send_link = Constante.url_weather+city;

        Log.d("TAG_WEATHER", "Weather json: "+Send_link);

        Ion.with(ctx)
                .load(Send_link)
                .setHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .setHeader("x-rapidapi-key", "46f10af993msh5cf8f7799dfd743p1e8e36jsn1db231f33793")
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            mainFunction(result);
                            Log.e("TAG_WEATHER", "onSuccess: result " + result );
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
        WeatherRepository.ParseData(result);
    }

}
