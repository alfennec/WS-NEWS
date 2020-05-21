package com.fennec.dailynews.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaver {

    public String directoryName = "images";
    public String fileName = "image.png";
    public Context context;
    public boolean external = false;

    public ImageSaver(Context context, String directoryName, String fileName)
    {
        this.context = context;
        this.directoryName = directoryName;
        this.fileName = fileName;

        Log.e("TAG-PHOTO","constructor : " + directoryName+" --- "+fileName);
    }

    public void save(Bitmap bitmapImage)
    {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

            Log.d("TAG-PHOTO", "save: SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    Log.d("TAG-PHOTO", "save: FAILED 1");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG-PHOTO", "save: FAILED 2");
            }
        }
    }

    @NonNull
    private File createFile()
    {
        File directory;
        if(external)
        {
            directory = getAlbumStorageDir(directoryName);
        }
        else
            {
            directory = context.getDir(directoryName, Context.MODE_PRIVATE);
        }

        if(!directory.exists() && !directory.mkdirs()){
            Log.e("TAG-PHOTO","Error creating directory " + directory);
        }

        return new File(directory, fileName);
    }

    private File getAlbumStorageDir(String albumName)
    {
        return new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
    }

    public static boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean isExternalStorageReadable()
    {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /***** how to get it ***/

    //To save:

    /*new ImageSaver(context).
    setFileName("myImage.png").
    setDirectoryName("images").
    save(bitmap);*/

    //To load:

    /*Bitmap bitmap = new ImageSaver(context).
            setFileName("myImage.png").
            setDirectoryName("images").
            load();*/
}
