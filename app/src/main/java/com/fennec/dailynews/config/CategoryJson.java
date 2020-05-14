package com.fennec.dailynews.config;

import android.content.Context;
import android.util.Log;

import com.fennec.dailynews.controller.ui.category.CategoryFragment;
import com.fennec.dailynews.controller.ui.home.HomeFragment;
import com.fennec.dailynews.repository.CategoryRepository;
import com.fennec.dailynews.repository.NewsRepository;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class CategoryJson {

    public String method;
    public int choice_acty;

    public CategoryJson(String link , final Context ctx, String method, int choice_acty)
    {
        this.method = method;
        this.choice_acty = choice_acty;

        String Send_link = Constante.url_host+link;

        Log.d("TAG", "CATEGORY JSON: "+Send_link);

        Ion.with(ctx)
                .load(Send_link)
                .asString()
                .setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        if(result != null)
                        {
                            mainFunction(result);
                            //Log.e("TAG_JSON", "onSuccess: result " + result );
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

            case 2: getSuccess_home(result); break;

            default : break;
        }
    }

    public void getSuccess(String result)
    {
        if(CategoryRepository.ParseData(result))
        {
            CategoryFragment.onSucces();
        }
    }

    public void getSuccess_home(String result)
    {
        if(CategoryRepository.ParseData(result))
        {
            HomeFragment.onSuccesCategory();
        }
    }
}
