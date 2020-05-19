package com.fennec.dailynews.repository;

import android.content.Context;

import com.fennec.dailynews.entity.News;

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

    /**** in create activity **/
    /*
    boolean isFilePresent = isFilePresent(getActivity(), "storage.json");
        if(isFilePresent) {
           String jsonString = read(getActivity(), "storage.json");
           //do the json parsing here and do the rest of functionality of app
        } else {
           boolean isFileCreated = create(getActivity, "storage.json", "{}");
           if(isFileCreated) {
             //proceed with storing the first todo  or show ui
           } else {
             //show error or try again.
           }
        }
     */


    private String read(Context context, String fileName)
    {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }
    }

    private boolean create(Context context, String fileName, String jsonString)
    {
        String FILENAME = "storage.json";
        try {
            FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return true;
        } catch (FileNotFoundException fileNotFound) {
            return false;
        } catch (IOException ioException) {
            return false;
        }

    }

    public boolean isFilePresent(Context context, String fileName)
    {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        return file.exists();
    }
}
