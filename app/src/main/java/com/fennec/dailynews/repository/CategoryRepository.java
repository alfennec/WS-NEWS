package com.fennec.dailynews.repository;

import com.fennec.dailynews.entity.Category;

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
}
